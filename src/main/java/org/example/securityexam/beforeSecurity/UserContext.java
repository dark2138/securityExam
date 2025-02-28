package org.example.securityexam.beforeSecurity;

public class UserContext {
/*
private static final ThreadLocal<User> userThreadLocal = new ThreadLocal<>(){
    @Override
    protected User initialValue() {
        return super.initialValue();
    }
}
*/
    // 위 과정을 람다식으로 표현
    private static final ThreadLocal<User> userThreadLocal = ThreadLocal.withInitial(()->null);

    public static void setUser(User user) {
        userThreadLocal.set(user);
    }
    public static User getUser() {
        return userThreadLocal.get();
    }
    public static void clearUser() {
        userThreadLocal.remove();
    }
}
