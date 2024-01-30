package main.wordboundrewrite.utils.dataHandlers.server.send.requests;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

public abstract class RequestFactoryAbstractClass {
    private static final List<Request> requests = new LinkedList<>();
    public static URI url;
    public static boolean needToSend=false;
    public RequestFactoryAbstractClass(URI url){
        this.url=url;
    }
    public static String buildRequest(){
        StringBuilder builtRequest= new StringBuilder("[");
        boolean firstIterationFlag=true;
        for(Request request : requests){
            if(firstIterationFlag){
                firstIterationFlag=false;
                builtRequest.append(request.getRequest());
            }else{
                builtRequest.append(",");
                builtRequest.append(request.getRequest());
            }
        }
        builtRequest.append("]");
        return builtRequest.toString();
    }
    public static void addRequest(Request request) {
        needToSend=true;
        requests.add(request);
    }
}
