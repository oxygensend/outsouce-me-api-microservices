package com.oxygensened.userprofile.infrastructure.cache.serialization;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.util.DefaultInstantiatorStrategy;
import com.oxygensend.commonspring.PagedListView;
import com.oxygensened.userprofile.application.company.dto.CompanyView;
import com.oxygensened.userprofile.application.education.dto.view.EducationView;
import com.oxygensened.userprofile.application.jobposition.dto.view.JobPositionView;
import com.oxygensened.userprofile.application.language.dto.view.LanguageView;
import com.oxygensened.userprofile.application.profile.dto.view.AddressView;
import com.oxygensened.userprofile.application.profile.dto.view.DeveloperView;
import com.oxygensened.userprofile.application.profile.dto.view.UserView;
import com.oxygensened.userprofile.domain.entity.part.AccountType;
import com.oxygensened.userprofile.domain.entity.part.Experience;
import com.oxygensened.userprofile.domain.entity.part.FormOfEmployment;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class KryoContext {
    static private final ThreadLocal<Kryo> kryos = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        ((DefaultInstantiatorStrategy) kryo.getInstantiatorStrategy()).setFallbackInstantiatorStrategy(
            new StdInstantiatorStrategy());

        /*
         * Apparently reflections based serializer from kryo-serializers library doesn't work with JDK21
         * https://github.com/magro/kryo-serializers/issues/138 that is why unmodifiable list has to be transformed into mutable object
         */
        kryo.register(Collections.unmodifiableList(new ArrayList<>()).getClass(), new JdkImmutableListSerializer(), 9);
        kryo.register(UserView.class, 10);
        kryo.register(EducationView.class, 11);
        kryo.register(LanguageView.class, 12);
        kryo.register(DeveloperView.class, 13);
        kryo.register(PagedListView.class, 14);
        kryo.register(JobPositionView.class, 15);
        kryo.register(AddressView.class, 16);
        kryo.register(Experience.class, 17);
        kryo.register(AccountType.class, 18);
        kryo.register(EducationView.UniversityView.class, 19);
        kryo.register(LocalDateTime.class, 20);
        kryo.register(LocalDate.class, 21);
        kryo.register(CompanyView.class, 22);
        kryo.register(FormOfEmployment.class, 23);
        kryo.register(List.of().getClass(), 24);
        kryo.register(Set.of().getClass(), 25);

        return kryo;
    });

    public static Kryo get() {
        return kryos.get();
    }
}
