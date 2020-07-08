package src.Response;

/*
@author: tom.cui
@date 2020/4/16
@description: 公共响应基类
*/
public class ResponseModel<T> {

    private T data;

    private boolean success;

    private String message;

    private int code;

    public ResponseModel<T> auto(boolean success){
        if (success){
            return  ok();
        }else {
            return  fail();
        }
    }

    public ResponseModel<T> auto(boolean success,T data){
        if (success){
            return  ok(data);
        }else {
            return  fail();
        }
    }

    public ResponseModel<T> auto(boolean success,T data,String message){
        if (success){
            return  ok(data,message);
        }else {
            return  fail(message);
        }
    }


    public ResponseModel<T> ok(){
        ResponseModel<T> res=new ResponseModel<T>();
        res.setSuccess(true);
        res.setMessage("操作成功");
        return  res;
    }


    public ResponseModel<T> ok(T data){
        ResponseModel<T> res=new ResponseModel<T>();
        res.setSuccess(true);
        res.setMessage("操作成功");
        res.setData(data);
        return  res;
    }

    public ResponseModel<T> ok(T data,String message){
        ResponseModel<T> res=new ResponseModel<T>();
        res.setSuccess(true);
        res.setMessage(message);
        res.setData(data);
        return  res;
    }



    public ResponseModel<T> fail(){
        ResponseModel<T> res=new ResponseModel<T>();
        res.setSuccess(false);
        res.setMessage("操作失败");
        return  res;
    }

    public ResponseModel<T> fail(String message){
        ResponseModel<T> res=new ResponseModel<T>();
        res.setSuccess(false);
        res.setMessage("操作失败");
        return  res;
    }


    public ResponseModel<T> fail(T data){
        ResponseModel<T> res=new ResponseModel<T>();
        res.setSuccess(false);
        res.setMessage("操作失败");
        res.setData(data);
        return  res;
    }

    public ResponseModel<T> fail(T data,String message){
        ResponseModel<T> res=new ResponseModel<T>();
        res.setSuccess(false);
        res.setMessage(message);
        res.setData(data);
        return  res;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
