package com.zs.test.aspect;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-4-26 上午10:58
 * @description: mytest
 */
@Aspect
public class TestAspect {

    public static final String TAG = "TestAspect";


        //定义切点 call表示在函数调用点
        //切点类型 参考Join Point类型
        @Pointcut("call(* com.zs.test.aspect.TestJoinPoints.testCall(..))")
        public void testCall() {}

        //定义切点处的执行动作。 Before表示在切点之前
        @Before("testCall()")
        public void beforeTestCall(JoinPoint joinPoint) {
            Log.e(TAG, "beforeTestCall ! this： " + joinPoint.getThis().toString() + " target： " + joinPoint.getTarget().toString() + " arg： " + joinPoint.getArgs().toString());
            for (Object arg : joinPoint.getArgs()) {
                Log.e(TAG, arg.toString());
            }
        }

        //定义切点 execution类型表示在函数执行
        @Pointcut("execution(* com.zs.test.aspect.TestJoinPoints.testCall(..))")
        public void testExecution() {}

        //定义切点处的执行动作。 测试call切点和execution切点的不同之处
        @Before("testExecution()")
        public void beforeTestExecution(JoinPoint joinPoint) {
            //call和execution的区别就是 返回的Target对象和This对象不同
            Log.e(TAG, "beforeTestExecution ! this： " + joinPoint.getThis().toString() + " target： " + joinPoint.getTarget().toString() + " arg： " + joinPoint.getArgs().toString());
            for (Object arg : joinPoint.getArgs()) {
                Log.e(TAG, arg.toString());
            }
        }

        //定义切点处的执行动作Around。 可以获取参数修改参数。
        @Around("testExecution()")
        public void aroundTestCall(ProceedingJoinPoint joinPoint) {
            Log.e(TAG, "aroundTest");
            try {
                //获取参数
                for (Object arg : joinPoint.getArgs()) {
                    if (arg != null) {
                        Log.e(TAG, "原来参数 " + arg.toString());
                    }
                }
                Object[] arg = new Object[1];
                arg[0] = 1000;
                Log.e(TAG, "修改参数 " + arg[0]);
                //调用原来方法的处理
                joinPoint.proceed(arg);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }


        //定义切点
        @Pointcut("call(* com.zs.test.aspect.TestJoinPoints.testReturnValue(..))")
        public void testReturnValue() {}

        //定义切点处的执行动作AfterReturning 在返回值后植入代码 并且获取返回值
        @AfterReturning(pointcut = "testReturnValue()", returning = "result")
        public void handleReturnValue(JoinPoint joinPoint, Object result) {
            Log.e(TAG, "获取返回值 " + result.toString());
        }


        //获取未捕获异常的切点
        @Pointcut("call(* com.zs.test.aspect.TestJoinPoints.testUnCatchException(..))")
        public void testUnCatchException() {}

        @AfterThrowing(pointcut = "testUnCatchException()", throwing = "ex")
        public void handletestAspectCallReturnValue(JoinPoint joinPoint, Object ex) {
            Log.e(TAG, ex.toString());
        }


        //within 接受的参数是类签名，表示该类中所有可以切入的JPoint，一般用于过滤条件。
        // 例如下面表达式表示testCall方法的切入点，但是排除掉TestJoinPoints中的该方法
        //withincode 和within用法是一样的，但是其表示的是方法内的JPoint，其接受的signature是方法，而within接受的是类。
        @Pointcut("!within(com.zs.test.aspect.TestJoinPoints) && execution(* com.zs.test.aspect.TestJoinPoints2.testCall(..))")
        public void testWithin() {}

        @Before("testWithin()")
        public void beforeTestWithin(JoinPoint joinPoint) {
            Log.e(TAG, joinPoint.toString());
            Log.e(TAG, joinPoint.getSourceLocation().toString());
        }


        @Pointcut("call(* com.zs.test.aspect.TestJoinPoints.testFlow(..))")
        public void testCallFlow() {}

    //过滤testFlow方法的JPoint中非testCall的call是所有JPoint。
    //其实cflow也好within也好都是为了定位插入点的。需要写不同的判断条件。
    //cflow和cflowbelow区别在于会不会包含切入点方法的JPoint
    @Pointcut("cflow( call(* com.zs.test.aspect.TestJoinPoints.testFlow(..)) ) && !within(com.zs.test.aspect.TestAspect) && !call(* com.zs.test.aspect.TestJoinPoints.testCall(..))")
    public void testFlow() {}


    @Before("testFlow()")
    public void beforeTestFlow(JoinPoint joinPoint) {
        Log.e(TAG, joinPoint.toString());
        Log.e(TAG, joinPoint.getSourceLocation().toString());
    }


}
