# 🎯 Java Strategy Design Pattern

> 💡 **Goal:** Master Strategy Pattern from scratch for Java interviews using simple explanations, visual notes, and easy Java code.

---

---

# 📌 1. What Is Strategy?

> 🧩 **Strategy is a behavioral design pattern that defines a family of algorithms, puts each one in a separate class, and makes them interchangeable at runtime.**

In simple words:

> **Same task -> different ways to do it -> choose one at runtime**

Example idea:

```java
PaymentStrategy strategy = new CreditCardPayment();
ShoppingCart cart = new ShoppingCart(strategy);
cart.checkout(5000);
```

The client can switch behavior without changing the main class.

---

# 🤔 2. Why Do We Need Strategy?

Strategy is useful when one class has many possible behaviors and you do not want a large `if-else` or `switch` block.

Without Strategy:

```java
if (paymentType.equals("CARD")) {
    // card logic
} else if (paymentType.equals("UPI")) {
    // upi logic
} else if (paymentType.equals("PAYPAL")) {
    // paypal logic
}
```

This becomes hard to maintain as options grow.

With Strategy:

```java
PaymentStrategy strategy = new UpiPayment();
ShoppingCart cart = new ShoppingCart(strategy);
cart.checkout(5000);
```

> 🚨 **Problem Strategy solves:**  
> It removes hardcoded conditional behavior and makes algorithms interchangeable.

---

# 🚗 3. Real-Life Analogy

> 🚗 **Navigation Route Analogy**  
> You want to reach the same destination, but you can choose:
>
> - fastest route
> - shortest route
> - toll-free route
>
> The goal stays the same, but the strategy changes.

In code:

- Destination = final task
- Different routes = different strategies
- Maps app = context

> 💡 **Simple meaning:**  
> Strategy means choosing one way of doing something from many possible ways.

---

# 🎯 4. When To Use Strategy

| ✅ Use Strategy When | Example |
|---|---|
| A task has multiple possible algorithms | Sorting choices |
| Behavior should change at runtime | Payment method |
| Large `if-else` logic is growing | Discount calculation |
| You want to follow Open/Closed Principle | New pricing rule |
| Similar behaviors should be isolated into separate classes | Compression algorithms |

---

# 🚫 5. When NOT To Use Strategy

| ❌ Avoid Strategy When | Why |
|---|---|
| Only one simple behavior exists | Extra classes are unnecessary |
| Behavior never changes | Direct implementation is simpler |
| The logic is tiny and unlikely to grow | Pattern may be overengineering |
| Creating many small classes reduces clarity | Design may become noisy |
| Client should not choose behavior explicitly | Another pattern may fit better |

> ⚠️ **Beginner Warning:**  
> Strategy is powerful, but do not use it for very small logic that will never vary.

---

# ✅ 6. Advantages and Disadvantages

| ✅ Advantages | ❌ Disadvantages |
|---|---|
| Removes large conditional logic | Creates more classes |
| Behavior can change at runtime | Client must know available strategies |
| Supports Open/Closed Principle | Can feel heavy for simple cases |
| Encourages composition over inheritance | More objects to manage |
| Each algorithm stays isolated and testable | Too many tiny strategies can clutter design |

---

# 🧠 7. Core Roles In Strategy

| Role | Meaning |
|---|---|
| `Strategy` | Common interface for all algorithms |
| `ConcreteStrategy` | Specific implementation of an algorithm |
| `Context` | Uses a strategy object to perform work |
| `Client` | Chooses or supplies the strategy |

---

# 💻 8. Java Implementations

> 🧠 **Important Java interview point:**  
> Strategy uses **composition**, not inheritance, to switch behavior.

---

<details open>
<summary>🔽 1. Basic Strategy Example (Payment)</summary>

## Payment Strategy Example

This is the easiest interview-friendly example.

```java
interface PaymentStrategy {
    void pay(int amount);
}

class CreditCardPayment implements PaymentStrategy {
    @Override
    public void pay(int amount) {
        System.out.println("Paid " + amount + " using Credit Card");
    }
}

class UpiPayment implements PaymentStrategy {
    @Override
    public void pay(int amount) {
        System.out.println("Paid " + amount + " using UPI");
    }
}

class PaypalPayment implements PaymentStrategy {
    @Override
    public void pay(int amount) {
        System.out.println("Paid " + amount + " using PayPal");
    }
}

class ShoppingCart {
    private final PaymentStrategy paymentStrategy;

    public ShoppingCart(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void checkout(int amount) {
        paymentStrategy.pay(amount);
    }
}
```

Client code:

```java
public class Main {
    public static void main(String[] args) {
        ShoppingCart cart1 = new ShoppingCart(new CreditCardPayment());
        cart1.checkout(5000);

        ShoppingCart cart2 = new ShoppingCart(new UpiPayment());
        cart2.checkout(3000);
    }
}
```

### ✅ Why this is useful

- Payment logic is separated
- New payment methods can be added easily
- Context does not need `if-else` logic

</details>

---

<details>
<summary>🔽 2. Strategy With Setter (Runtime Switching)</summary>

## Runtime Switching Example

You can also change strategy while the program is running.

```java
interface SortStrategy {
    void sort(int[] numbers);
}

class BubbleSortStrategy implements SortStrategy {
    @Override
    public void sort(int[] numbers) {
        System.out.println("Sorting using Bubble Sort");
    }
}

class QuickSortStrategy implements SortStrategy {
    @Override
    public void sort(int[] numbers) {
        System.out.println("Sorting using Quick Sort");
    }
}

class Sorter {
    private SortStrategy strategy;

    public void setStrategy(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public void sortNumbers(int[] numbers) {
        if (strategy == null) {
            throw new IllegalStateException("Strategy not set");
        }
        strategy.sort(numbers);
    }
}
```

Usage:

```java
public class Demo {
    public static void main(String[] args) {
        Sorter sorter = new Sorter();

        sorter.setStrategy(new BubbleSortStrategy());
        sorter.sortNumbers(new int[]{5, 2, 9});

        sorter.setStrategy(new QuickSortStrategy());
        sorter.sortNumbers(new int[]{5, 2, 9});
    }
}
```

### 📌 Key idea

Same context, different algorithm, selected at runtime.

</details>

---

<details>
<summary>🔽 3. Java 8 Lambda Style Strategy</summary>

## Lambda-Based Strategy

Since Strategy is usually based on an interface with one method, Java 8 lambdas can make it shorter.

```java
interface DiscountStrategy {
    double applyDiscount(double price);
}

public class Main {
    public static void main(String[] args) {
        DiscountStrategy festivalDiscount = price -> price * 0.8;
        DiscountStrategy premiumDiscount = price -> price * 0.7;

        System.out.println(festivalDiscount.applyDiscount(1000));
        System.out.println(premiumDiscount.applyDiscount(1000));
    }
}
```

### ✅ Why mention this in interviews

- Shows modern Java understanding
- Strategy can be implemented with lambdas for simple cases

</details>

---

# 📊 9. Without Strategy vs With Strategy

| Topic | Without Strategy | With Strategy |
|---|---|---|
| Behavior selection | Large `if-else` blocks | Separate strategy classes |
| Extensibility | Harder | Easier |
| Readability | Lower as logic grows | Higher |
| Testing | Mixed logic in one place | Each strategy testable separately |
| Runtime flexibility | Low | High |

---

# 🔄 10. Flow Of Strategy

```text
Client -> Context -> Strategy
                  -> ConcreteStrategy
```

Step by step:

1. Client chooses a strategy
2. Client gives that strategy to the context
3. Context delegates work to the strategy
4. Strategy performs the selected algorithm

> 📌 **Memory hook:**  
> Same job, different methods.

---

# 🧱 11. Strategy vs Similar Patterns

| Pattern | Main Purpose |
|---|---|
| Strategy | Switches algorithms/behaviors |
| State | Changes behavior based on internal state |
| Template Method | Defines algorithm structure in a base class |
| Command | Encapsulates a request as an object |
| Decorator | Adds behavior by wrapping objects |

> ⚠️ **Most common confusion:**  
> Strategy is about choosing **how to do a task**.  
> State is about changing behavior based on **object state**.

---

# 🧱 12. Notion Column Layout Idea

Use this as a 2-column revision section after importing into Notion:

| 🧠 Remember | ⚠️ Avoid |
|---|---|
| Strategy replaces large conditional logic | Using it for one tiny fixed behavior |
| Algorithms are interchangeable | Confusing it with State |
| Uses composition | Creating too many unnecessary strategies |
| Behavior can change at runtime | Keeping logic hardcoded in context |

---

# 🔁 13. Synced Block Content

> 📌 **Copy this into a Notion Synced Block:**  
> Strategy Pattern defines a family of algorithms, puts each one in a separate class, and makes them interchangeable.  
> It is useful when the same task can be done in multiple ways.

---

# 🧩 14. Practice Template

## Strategy Pattern Revision Template

- **Definition:** Strategy lets you switch between different algorithms using a common interface.
- **Main problem solved:** Large conditional logic for multiple behaviors.
- **Real example:** Payment methods, route selection, sorting choices, discount rules.
- **Main rule:** Context delegates work to a strategy object.
- **Main benefit:** Flexible runtime behavior.

### ☑️ Checklist

- [ ] I can define Strategy simply
- [ ] I can explain Strategy, ConcreteStrategy, and Context
- [ ] I can write a payment strategy example
- [ ] I know why Strategy replaces `if-else`
- [ ] I can compare Strategy with State
- [ ] I can mention lambda-based strategy in Java

---

# 🎤 15. Common Interview Questions

| Question | Short Answer |
|---|---|
| What is Strategy Pattern? | A behavioral pattern that defines interchangeable algorithms behind a common interface. |
| Why do we use Strategy? | To remove large conditional logic and switch behavior easily. |
| What problem does Strategy solve? | Hardcoded algorithm selection in one class. |
| Does Strategy use inheritance or composition? | Composition. |
| Can Strategy change behavior at runtime? | Yes. That is one of its main benefits. |
| Strategy vs State? | Strategy is chosen behavior; State depends on internal state changes. |
| Strategy vs Template Method? | Strategy uses composition; Template Method uses inheritance. |
| Give one real Java use case. | Payment method selection or sorting algorithm selection. |
| When should you avoid Strategy? | When behavior is simple, fixed, and unlikely to vary. |
| What is the context in Strategy? | The class that uses a strategy object to perform work. |

---

# 📝 16. Revision Cheat Sheet

## 🧠 Core Formula

```java
Context context = new Context(new ConcreteStrategy());
context.execute();
```

## ⭐ Memory Hook

> **Strategy = same goal, different algorithm**

## ✅ Key Takeaways

- Strategy is a **behavioral design pattern**
- It defines a **family of algorithms**
- Algorithms are **interchangeable**
- It uses **composition**
- It removes **large `if-else` logic**
- Behavior can change **at runtime**
- Great for **payments, sorting, discounts, routing**

## ⚠️ Common Beginner Mistakes

- Confusing Strategy with State
- Using Strategy for tiny fixed logic
- Keeping algorithm logic inside the context
- Forgetting that the client usually chooses the strategy
- Creating too many unnecessary strategy classes

---

> 🎯 **Final Interview Line:**  
> "Strategy Pattern is used when the same task can be performed in multiple ways. It puts each algorithm into a separate class and lets the context use them interchangeably through a common interface, which removes large conditional logic and makes behavior flexible at runtime."
