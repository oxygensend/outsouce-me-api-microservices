package com.oxygensend.joboffer.infrastructure.cache.serialization;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.util.DefaultInstantiatorStrategy;
import com.oxygensend.commonspring.PagedListView;
import com.oxygensend.joboffer.application.application.dto.view.ApplicationInfoView;
import com.oxygensend.joboffer.application.application.dto.view.ApplicationListView;
import com.oxygensend.joboffer.application.application.dto.view.ApplicationView;
import com.oxygensend.joboffer.application.application.dto.view.AttachmentView;
import com.oxygensend.joboffer.application.job_offer.dto.view.AddressView;
import com.oxygensend.joboffer.application.job_offer.dto.view.BaseJobOfferView;
import com.oxygensend.joboffer.application.job_offer.dto.view.JobOfferDetailsView;
import com.oxygensend.joboffer.application.job_offer.dto.view.JobOfferInfoView;
import com.oxygensend.joboffer.application.job_offer.dto.view.JobOfferOrderView;
import com.oxygensend.joboffer.application.job_offer.dto.view.JobOfferView;
import com.oxygensend.joboffer.application.job_offer.dto.view.JobOfferWithUserView;
import com.oxygensend.joboffer.application.job_offer.dto.view.SalaryRangeView;
import com.oxygensend.joboffer.application.user.dto.view.BaseUserView;
import com.oxygensend.joboffer.application.user.dto.view.UserDetailsView;
import com.oxygensend.joboffer.application.user.dto.view.UserView;
import com.oxygensend.joboffer.domain.entity.part.ApplicationStatus;
import com.oxygensend.joboffer.domain.entity.part.Experience;
import com.oxygensend.joboffer.domain.entity.part.FormOfEmployment;
import com.oxygensend.joboffer.domain.entity.part.SupportedCurrency;
import com.oxygensend.joboffer.domain.entity.part.WorkType;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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
        kryo.register(JobOfferView.class, 10);
        kryo.register(JobOfferDetailsView.class, 11);
        kryo.register(SalaryRangeView.class,12);
        kryo.register(AddressView.class, 13);
        kryo.register(BaseUserView.class, 14);
        kryo.register(JobOfferWithUserView.class, 15);
        kryo.register(BaseJobOfferView.class, 16);
        kryo.register(UserDetailsView.class,17);
        kryo.register(UserView.class, 18);
        kryo.register(ApplicationView.class, 19);
        kryo.register(JobOfferWithUserView.class, 20);
        kryo.register(AttachmentView.class, 21);
        kryo.register(ApplicationInfoView.class, 22);
        kryo.register(ApplicationListView.class, 23);
        kryo.register(JobOfferInfoView.class, 24);
        kryo.register(JobOfferOrderView.class, 25);
        kryo.register(ApplicationStatus.class, 26);
        kryo.register(Experience.class, 27);
        kryo.register(SupportedCurrency.class, 28);
        kryo.register(FormOfEmployment.class, 29);
        kryo.register(WorkType.class,30);
        kryo.register(BigDecimal.class, 31);
        kryo.register(PagedListView.class, 33);
        kryo.register(LocalDateTime.class, 34);
        kryo.register(LocalDate.class, 35);
        kryo.register(Set.of().getClass(), 36);
        kryo.register(List.of().getClass(), 37);
        kryo.register(HashSet.class, 38);
        return kryo;
    });

    public static Kryo get() {
        return kryos.get();
    }
}
