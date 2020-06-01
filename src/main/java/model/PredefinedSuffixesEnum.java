package model;

public enum PredefinedSuffixesEnum {
    AUDIO {
        @Override
        public ISuffixesCategory getSuffixes() {
            return createSuffixesCategory(name(), Constants.audioSuffixes);
        }
    },
    VIDEO {
        @Override
        public ISuffixesCategory getSuffixes() {
            return createSuffixesCategory(name(), Constants.videoSuffixes);
        }
    },
    AUDIO_AND_VIDEO {
        @Override
        public ISuffixesCategory getSuffixes() {
            String combinedAudioAndVideo = Constants.audioSuffixes + "," + Constants.videoSuffixes;
            return createSuffixesCategory("AUDIO_AND_VIDEO", combinedAudioAndVideo);
        }
    };

    private static class Constants {
        static String audioSuffixes = "mp3,mpa";
        static String videoSuffixes = "avi,mp4";
    }

    protected ISuffixesCategory createSuffixesCategory(String categoryName, String delimitedSuffixes) {
        ISuffixesCategory category = new SimpleModelSuffixesCategoryImpl(categoryName);
        category.addSuffixes(delimitedSuffixes, ",");
        return category;
    }

    abstract public ISuffixesCategory getSuffixes();


}
