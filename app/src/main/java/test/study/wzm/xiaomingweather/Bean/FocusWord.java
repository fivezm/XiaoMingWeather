package test.study.wzm.xiaomingweather.Bean;

public class FocusWord {
    /**
     * result : {"famous_name":"王尔德","famous_saying":"感情的长处在于会使我们迷失方向，而科学的长处就在于它是不动感情的。"}
     * error_code : 0
     * reason : Succes
     */

    private ResultBean result;
    private int error_code;
    private String reason;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public static class ResultBean {
        /**
         * famous_name : 王尔德
         * famous_saying : 感情的长处在于会使我们迷失方向，而科学的长处就在于它是不动感情的。
         */

        private String famous_name;
        private String famous_saying;

        public String getFamous_name() {
            return famous_name;
        }

        public void setFamous_name(String famous_name) {
            this.famous_name = famous_name;
        }

        public String getFamous_saying() {
            return famous_saying;
        }

        public void setFamous_saying(String famous_saying) {
            this.famous_saying = famous_saying;
        }
    }
}
