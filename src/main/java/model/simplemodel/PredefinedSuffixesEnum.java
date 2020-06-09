package model.simplemodel;

import model.ISuffixesCollection;

public enum PredefinedSuffixesEnum {
    AUDIO {
        @Override
        public ISuffixesCollection getSuffixes() {
            return createSuffixesCategory(name(), Constants.audioSuffixes);
        }
    },
    VIDEO {
        @Override
        public ISuffixesCollection getSuffixes() {
            return createSuffixesCategory(name(), Constants.videoSuffixes);
        }
    },
    AUDIO_AND_VIDEO {
        @Override
        public ISuffixesCollection getSuffixes() {
            String combinedAudioAndVideo = Constants.audioSuffixes + "," + Constants.videoSuffixes;
            return createSuffixesCategory("AUDIO_AND_VIDEO", combinedAudioAndVideo);
        }
    };

    private static class Constants {
        static String audioSuffixes = "mp3,mpa";
        static String videoSuffixes = "avi,mp4";
    }

    protected ISuffixesCollection createSuffixesCategory(String categoryName, String delimitedSuffixes) {
        ISuffixesCollection category = new SimpleModelSuffixesCollectionImpl(categoryName);
        category.addSuffixes(delimitedSuffixes, ",");
        return category;
    }

    abstract public ISuffixesCollection getSuffixes();


}
