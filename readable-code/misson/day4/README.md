# 미션 - Day4

<!-- TOC -->
* [미션 - Day4](#미션---day4)
    * [SOLID에 대하여 자기만의 언어로 정리](#solid에-대하여-자기만의-언어로-정리)
        * [SRP (Single Responsibility Principle) : 단일 책임 원칙](#srp-single-responsibility-principle--단일-책임-원칙)
        * [OCP (Open-Closed Principle) : 개방 폐쇄 원칙](#ocp-open-closed-principle--개방-폐쇄-원칙)
        * [LSP (Liskov Substitution Principle) : 리스코프 치환 원칙](#lsp-liskov-substitution-principle--리스코프-치환-원칙)
        * [ISP (Interface Segregation Principle) : 인터페이스 분리 원칙](#isp-interface-segregation-principle--인터페이스-분리-원칙)
        * [DIP (Dependency Inversion Principle) : 의존성 역전 원칙](#dip-dependency-inversion-principle--의존성-역전-원칙)
<!-- TOC -->

## SOLID에 대하여 자기만의 언어로 정리

### SRP (Single Responsibility Principle) : 단일 책임 원칙

**"하나의 클래스는 하나의 역할만 해야 한다."**

> "커피머신이 커피만 내려야 하는데, 손님 주문도 받고, 회계 처리까지 한다면? 유지보수하기 어려운 코드가 된다."

```java
// SRP 위반
public class CoffeeMachine {
    public void brewCoffee() {
        System.out.println("커피를 내립니다.");
    }
    
    public void takeOrder() {
        System.out.println("손님 주문을 받습니다.");
    }
}
```

```java
// SRP 적용
public class CoffeeMachine {
    public void brewCoffee() {
        System.out.println("커피를 내립니다.");
    }
}

public class OrderHandler {
    public void takeOrder() {
        System.out.println("손님 주문을 받습니다.");
    }
}
```

---

### OCP (Open-Closed Principle) : 개방 폐쇄 원칙

**"확장에는 열려 있고, 수정에는 닫혀 있어야 한다."**

> "새로운 기능을 추가할 때 기존 코드를 변경하지 말고 확장할 수 있도록 설계해야 한다."

```java
// OCP 위반
public class NotificationService {
    public void sendNotification(String type) {
        if (type.equals("SMS")) {
            System.out.println("SMS 발송");
        } else if (type.equals("EMAIL")) {
            System.out.println("이메일 발송");
        }
    }
}
```

```java
// OCP 적용
public interface Notification {
    void send();
}

public class SmsNotification implements Notification {
    public void send() {
        System.out.println("SMS 발송");
    }
}

public class EmailNotification implements Notification {
    public void send() {
        System.out.println("이메일 발송");
    }
}

public class NotificationService {
    public void sendNotification(Notification notification) {
        notification.send();
    }
}
```

---

### LSP (Liskov Substitution Principle) : 리스코프 치환 원칙

**"자식 클래스는 부모 클래스를 대체할 수 있어야 한다."**

> "USB-C 충전기를 사용하려고 했는데, 특정 브랜드 제품만 충전이 된다면? 이 원칙이 깨진 것이다."

```java
// LSP 위반
public class Bird {
    public void fly() {
        System.out.println("새가 날아갑니다.");
    }
}

public class Penguin extends Bird {
    @Override
    public void fly() {
        throw new UnsupportedOperationException("펭귄은 날 수 없습니다.");
    }
}
```

```java
// LSP 적용
public interface Flyable {
    void fly();
}

public class Sparrow implements Flyable {
    public void fly() {
        System.out.println("참새가 날아갑니다.");
    }
}

public class Penguin {
    public void swim() {
        System.out.println("펭귄이 헤엄칩니다.");
    }
}
```

---

### ISP (Interface Segregation Principle) : 인터페이스 분리 원칙

**"클라이언트가 필요하지 않은 메서드에 의존하지 않도록 인터페이스를 분리해야 한다."**

> "TV 리모컨에 세탁기 버튼이 붙어 있다면? 불필요한 기능을 강제하는 것과 같다."

```java
// ISP 위반
public interface Device {
    void turnOn();
    void turnOff();
    void setTemperature(int temp); // 불필요한 기능
}
```

```java
// ISP 적용
public interface Switchable {
    void turnOn();
    void turnOff();
}

public interface TemperatureControllable {
    void setTemperature(int temp);
}
```

---

### DIP (Dependency Inversion Principle) : 의존성 역전 원칙

**"상위 모듈이 하위 모듈에 의존하지 않고, 추상화에 의존해야 한다."**

> "멀티탭을 사용할 때 특정 브랜드의 전자제품만 꽂을 수 있다면? 확장성이 떨어진다."

```java
// DIP 위반
public class Keyboard {
    public void connectToComputer() {
        System.out.println("키보드를 컴퓨터에 연결합니다.");
    }
}

public class Computer {
    private final Keyboard keyboard = new Keyboard();
}
```

```java
// DIP 적용
public interface InputDevice {
    void connect();
}

public class Keyboard implements InputDevice {
    public void connect() {
        System.out.println("키보드를 연결합니다.");
    }
}

public class Computer {
    private final InputDevice inputDevice;
    
    public Computer(InputDevice inputDevice) {
        this.inputDevice = inputDevice;
    }
}
