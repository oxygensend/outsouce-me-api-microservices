package com.oxygensened.userprofile.application.storage;

import org.apache.commons.lang3.ObjectUtils;

public record ThumbnailOptions(String outputFormat,
                               int height,
                               int weight,
                               float quality) {

    public ThumbnailOptions {
        if (height < 0 || weight < 0 || quality < 0 || quality > 100 || ObjectUtils.isEmpty(outputFormat)) {
            throw new IllegalArgumentException("Invalid thumbnail options");
        }
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String outputFormat = "webp";
        private int height = 0;
        private int weight = 0;
        private float quality = 1.0F;

        public Builder outputFormat(String outputFormat) {
            this.outputFormat = outputFormat;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder weight(int weight) {
            this.weight = weight;
            return this;
        }

        public Builder quality(float quality) {
            this.quality = quality;
            return this;
        }

        public ThumbnailOptions build() {
            return new ThumbnailOptions(outputFormat, height, weight, quality);
        }
    }
}
