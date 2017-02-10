package com.quascenta.petersroad.droidtag.EventBus;

/**
 * Created by AKSHAY on 2/10/2017.
 */

public class Events {

    public static class FragmentActivityMessage {
        private String message;
        private float low_limit;
        private float high_limit;

        public FragmentActivityMessage() {
        }

        public FragmentActivityMessage(float lowerlimit, float upperlimit) {
            this.low_limit = lowerlimit;
            this.high_limit = upperlimit;
        }

        public float getLow_limit() {
            return low_limit;
        }

        public void setLow_limit(float low_limit) {
            this.low_limit = low_limit;
        }

        public float getHigh_limit() {
            return high_limit;
        }

        public void setHigh_limit(float high_limit) {
            this.high_limit = high_limit;
        }

        public String getMessage() {
            return message;
        }
    }

    // Event used to send message from activity to fragment.
    public static class ActivityFragmentMessage {
        private String message;
        private float low_limit;
        private float high_limit;

        public ActivityFragmentMessage(float lowerlimit, float upperlimit) {

            this.low_limit = lowerlimit;
            this.high_limit = upperlimit;
        }

        public float getLow_limit() {
            return low_limit;
        }

        public float getHigh_limit() {
            return high_limit;
        }
    }

    // Event used to send message from activity to activity.
    public static class ActivityActivityMessage {
        private String message;

        public ActivityActivityMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }


}
