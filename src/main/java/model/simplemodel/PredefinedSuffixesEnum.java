package model.simplemodel;

import model.ISuffixesCollection;

public enum PredefinedSuffixesEnum {
    AUDIO {
        @Override
        public ISuffixesCollection getSuffixes() {
            return createSuffixesCollection(name(), Constants.audioSuffixes);
        }
    },
    VIDEO {
        @Override
        public ISuffixesCollection getSuffixes() {
            return createSuffixesCollection(name(), Constants.videoSuffixes);
        }
    },
    AUDIO_AND_VIDEO {
        @Override
        public ISuffixesCollection getSuffixes() {
            String combinedAudioAndVideo = Constants.audioSuffixes + "," + Constants.videoSuffixes;
            return createSuffixesCollection(name(), combinedAudioAndVideo);
        }
    },
    ALL {
        @Override
        public ISuffixesCollection getSuffixes() {
            return SuffixesCollectionImpl.getAllSuffixCollection();
        }
    };

    private static final class Constants {
        static final String audioSuffixes = "mp3,mpa";
        static final String videoSuffixes = "avi,mp4";
    }

    protected ISuffixesCollection createSuffixesCollection(String categoryName, String delimitedSuffixes) {
        ISuffixesCollection category = new SuffixesCollectionImpl(categoryName);
        category.addSuffixes(delimitedSuffixes, ",");
        return category;
    }

    public abstract ISuffixesCollection getSuffixes();
}
