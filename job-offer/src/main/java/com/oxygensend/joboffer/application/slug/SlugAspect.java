package com.oxygensend.joboffer.application.slug;

import com.oxygensend.joboffer.domain.Slug;
import com.oxygensend.joboffer.domain.entity.JobOffer;
import com.oxygensend.joboffer.domain.service.SlugService;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.StringJoiner;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

// TODO migrate to PreUpdateEventListener
@Aspect
@Component
final class SlugAspect {
    private final SlugService slugService;

    SlugAspect(SlugService slugService) {
        this.slugService = slugService;
    }

    @Before("execution(* org.springframework.data.repository.CrudRepository+.save(*))")
    public void beforeRepositorySave(JoinPoint joinPoint) {
        var entity = joinPoint.getArgs()[0];

        if (!isEntitySupported(entity)) {
            return;
        }

        try {
            for (var field : entity.getClass().getDeclaredFields()) {
                var annotation = field.getAnnotation(Slug.class);
                if (annotation == null) {
                    continue;
                }

                var slug = createSlug(entity, annotation);
                var currentSlug = getSlug(entity);

                if (currentSlug != null && currentSlug.startsWith(slug)) { // this means that the slug is not changed
                    return;
                }

                var repository = (CrudRepository<?, ?>) joinPoint.getTarget();
                long slugVersion = calculateSlugVersion(slug, repository);
                if (slugVersion >= 0) {
                    slug = slug + "-" + (slugVersion + 1);
                }

                setSlug(slug, entity);
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

    }

    private boolean isEntitySupported(Object entity) {
        return entity instanceof JobOffer;
    }

    private String createSlug(Object entity, Slug annotation) throws NoSuchFieldException, IllegalAccessException {
        StringJoiner stringJoiner = new StringJoiner("-");
        for (var property : annotation.source()) {
            var tempField = entity.getClass().getDeclaredField(property);
            tempField.setAccessible(true);
            String s = (String) tempField.get(entity);
            stringJoiner.add(s);
        }

        return slugService.createSlug(stringJoiner.toString());
    }


    private long calculateSlugVersion(String slug, CrudRepository<?, ?> repository) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method countBySlug = repository.getClass().getMethod("findTheNewestSlugVersion", String.class);
        countBySlug.setAccessible(true);
        return (long) countBySlug.invoke(repository, slug);
    }

    private void setSlug(String slug, Object entity) throws NoSuchFieldException, IllegalAccessException {
        var field = entity.getClass().getDeclaredField("slug");
        field.setAccessible(true);
        field.set(entity, slug);
    }

    private String getSlug(Object entity) throws IllegalAccessException, NoSuchFieldException {
        var field = entity.getClass().getDeclaredField("slug");
        field.setAccessible(true);
        return (String) field.get(entity);
    }
}
