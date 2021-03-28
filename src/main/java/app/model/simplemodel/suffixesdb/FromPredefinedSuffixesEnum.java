package app.model.simplemodel.suffixesdb;

import app.model.ISuffixes;
import app.model.simplemodel.AllSuffixes;
import app.model.simplemodel.SuffixesImpl;

public enum FromPredefinedSuffixesEnum {
    AUDIO {
        @Override
        public ISuffixes getSuffixes() {
            return createSuffixes(name(), Constants.audioSuffixes);
        }
    },
    VIDEO {
        @Override
        public ISuffixes getSuffixes() {
            return createSuffixes(name(), Constants.videoSuffixes);
        }
    },
    AUDIO_AND_VIDEO {
        @Override
        public ISuffixes getSuffixes() {
            String combinedAudioAndVideo = Constants.audioSuffixes + "," + Constants.videoSuffixes;
            return createSuffixes(name(), combinedAudioAndVideo);
        }
    },
    ALL {
        @Override
        public ISuffixes getSuffixes() {
            return new AllSuffixes();
        }
    };

    private static final class Constants {
        static final String audioSuffixes = "mp3,mpa";
        static final String videoSuffixes = "avi,mp4";
    }

    protected ISuffixes createSuffixes(String categoryName, String delimitedSuffixes) {
        ISuffixes category = new SuffixesImpl(categoryName);
        category.addSuffixes(delimitedSuffixes, ",");
        return category;
    }

    public abstract ISuffixes getSuffixes();
}