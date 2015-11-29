package shagold.wifwaf.view;

/**
 * Created by jimmy on 29/11/15.
 */
public enum ErrorMessage {

    BLANK {
        @Override
        public String toString() {
            return "Not blank message";
        }
    },

    NUMBER {
        @Override
        public String toString() {
            return "No number in text";
        }
    },

    SIZE {
        @Override
        public String toString() {
            return "Wrong size of text";
        }
    },




}
