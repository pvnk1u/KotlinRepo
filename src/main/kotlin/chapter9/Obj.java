package chapter9;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Obj extends Parent{

    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public static void main(String[] args) {
        List<Obj> list = new ArrayList<>();
        /**
         *  map方法接口签名为：<R> Stream<R> map(Function<? super T, ? extends R> mapper);
         *  所以这里map中可以选择指定Obj父类Parent中的getGroupName方法
         */
        List<String> result = list.stream().map(Parent::getGroupName).collect(Collectors.toList());
    }
}
