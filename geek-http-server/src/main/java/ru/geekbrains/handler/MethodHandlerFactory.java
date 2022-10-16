package ru.geekbrains.handler;

import org.reflections.Reflections;
import ru.geekbrains.service.ResponseSerializer;
import ru.geekbrains.config.Config;
import ru.geekbrains.service.SocketService;

import java.lang.reflect.AnnotatedElement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MethodHandlerFactory {
    public static MethodHandler create(SocketService socketService,
                                       ResponseSerializer responseSerializer, Config config){
        Reflections reflections = new Reflections("ru.geekbrains.handler");

        Set<Class<?>> ann = reflections.getTypesAnnotatedWith(Handler.class);
        Map<Integer, Object> objectMap = new HashMap<>();
        int maxOrder = 0;
        Iterator iterator = ann.iterator();
        while (iterator.hasNext()) {
            AnnotatedElement el = (AnnotatedElement) iterator.next();
            Handler annotation = el.getAnnotation(Handler.class);
            objectMap.put(annotation.order(), el);
            if (maxOrder < annotation.order()) {
                maxOrder = annotation.order();
            }
        }
        MethodHandler next = null;
        while (maxOrder > 0){
            next = createMethodHandler(objectMap.get(maxOrder).toString(),next,socketService,
                    responseSerializer,config);
            maxOrder--;
        }
        return next;

    }
    public static MethodHandler createMethodHandler(String clazz, MethodHandler next,SocketService socketService,
                                                    ResponseSerializer responseSerializer, Config config){
        String[] s = clazz.split(" ");
        s = s[1].split("\\.");
        String method = s[s.length-1].replaceAll("MethodHandler","");
        method = method.toUpperCase();
        MethodHandler methodHandler = null;
        switch (method){
            case "PUT": methodHandler = new PutMethodHandler(method, next, socketService,responseSerializer,config);
                break;
            case "POST": methodHandler = new PostMethodHandler(method, next, socketService,responseSerializer,config);
                break;
            case "GET": methodHandler = new GetMethodHandler(method, next, socketService,responseSerializer,config);
                break;
            default:    break;
        }
        return methodHandler;
    }

}
