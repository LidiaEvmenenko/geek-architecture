package ru.geekbrains.handler;

import org.reflections.Reflections;
import ru.geekbrains.config.Config;
import ru.geekbrains.service.ResponseSerializer;
import ru.geekbrains.service.SocketService;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AnnotatedHandlerFactory {
    private static MethodHandler prev = null;
    private static MethodHandler next = null;

    public static MethodHandler create(SocketService socketService,
                                       ResponseSerializer responseSerializer, Config config) {

        Reflections reflections = new Reflections("ru.geekbrains.handler");
        List<Class<?>> classes = new ArrayList<>(reflections.getTypesAnnotatedWith(Handler.class));
        classes.sort(Comparator.comparingInt(AnnotatedHandlerFactory::getOrder).reversed());
        try {

            for (Class<?> clazz : classes) {
                Constructor<?> constructor = clazz.getConstructor(String.class, MethodHandler.class,
                        SocketService.class, ResponseSerializer.class, Config.class);
                prev = (MethodHandler) constructor.newInstance(getMethod(clazz), prev,
                        socketService, responseSerializer, config);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return prev;
    }
    private static int getOrder(Class<?> clazz) {
        return clazz.getAnnotation(Handler.class).order();
    }

    private static String getMethod(Class<?> clazz) {
        return clazz.getAnnotation(Handler.class).method();
    }
    public static MethodHandler getPrev() {
        return prev;
    }
    public static MethodHandler getMethodHandlerNext(){
        return AnnotatedHandlerFactory.next.getNext();
    }

    public static void setSocketService(SocketService socketService) {
        next = prev;
        while (true) {
            AnnotatedHandlerFactory.next.socketService = socketService;
            next = AnnotatedHandlerFactory.next.getNext();
            if (next == null) break;
        }
    }
}
