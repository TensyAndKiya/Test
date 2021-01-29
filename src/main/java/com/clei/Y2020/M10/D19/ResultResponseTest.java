package com.clei.Y2020.M10.D19;

import com.alibaba.fastjson.JSONObject;
import com.clei.entity.Person;
import com.clei.utils.PrintUtil;

/**
 * 两个不同web项目的返回结果互相转换
 * 背景是因为，在不同时期，不同项目中用了不同的返回结果类型
 *
 * @author KIyA
 */
public class ResultResponseTest {

    public static void main(String[] args) {

        Person p = new Person("张三", 18, 1);

        Result<Person> result = new Result<>(true, "成功", "200", p);

        PrintUtil.log(result.getData().toString());

        PrintUtil.log(JSONObject.toJSONString(result));

        Response<Person> response = convert(result);

        Person person = response.getResult();

        PrintUtil.log(person.getClass().getName());

        PrintUtil.log(response.getResult().toString());

        PrintUtil.log(JSONObject.toJSONString(response));
    }

    /**
     * Result -> Response
     *
     * @param result
     * @param <T>
     * @return
     */
    public static <T> Response<T> convert(Result<T> result) {
        return new Response<T>(result.isSuccess(), result.getMessage(), result.getCode(), result.getData());
    }

    /**
     * Response -> Result
     *
     * @param response
     * @param <T>
     * @return
     */
    public static <T> Result<T> convert(Response<T> response) {
        return new Result<T>(response.isSuccess(), response.getMsg(), response.getCode(), response.getResult());
    }

    static class Result<T> {

        private boolean success;
        private String message;
        private String code;
        private T data;

        public Result() {
        }

        public Result(boolean success, String message, String code, T data) {
            this.success = success;
            this.message = message;
            this.code = code;
            this.data = data;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }

    static class Response<T> {

        private boolean success;
        private String msg;
        private String code;
        private T result;

        public Response() {
        }

        public Response(boolean success, String msg, String code, T result) {
            this.success = success;
            this.msg = msg;
            this.code = code;
            this.result = result;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public T getResult() {
            return result;
        }

        public void setResult(T result) {
            this.result = result;
        }
    }

}
