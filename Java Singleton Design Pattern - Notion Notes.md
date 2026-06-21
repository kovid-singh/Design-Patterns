# Java Singleton Design Pattern

> **Goal:** Master Singleton from scratch for Java interviews in a simple, visual, revision-friendly way.

---


---

# 1. What Is Singleton?

> **Singleton is a creational design pattern that allows a class to have only one object and gives a global access point to that object.**

In simple words:

> **One class -> One object -> Reused everywhere**

Example usage:

```java
Singleton obj = Singleton.getInstance();
```

The class itself controls object creation, so other classes cannot freely create new objects using `new`.

---

# 2. Why Do We Need Singleton?

Imagine your application has a logger.

Without Singleton:

```java
Logger logger1 = new Logger();
Logger logger2 = new Logger();
Logger logger3 = new Logger();
```

Now the app may have many logger objects doing the same job. This can waste memory and create inconsistent behavior.

With Singleton:

```java
Logger logger = Logger.getInstance();
```

Now the whole application uses the same logger object.

> **Problem Singleton solves:**  
> It prevents unnecessary multiple objects when only one shared object should exist.

---

# 3. Real-Life Analogy

> **School Principal Analogy**  
> A school usually has **one principal**.  
> Many teachers and students can contact the principal, but there should not be many principals controlling the same school.

Singleton works similarly:

- One important object exists
- Many parts of the program can access it
- Object creation is controlled

---

# 4. When To Use Singleton

| Use Singleton When | Example |
|---|---|
| You need exactly one shared object | Logger |
| Object creation is expensive | Configuration manager |
| A shared resource must be controlled | Cache manager |
| One global access point is useful | App settings |
| Multiple objects may cause inconsistency | Database connection manager |

---

# 5. When NOT To Use Singleton

| Avoid Singleton When | Why |
|---|---|
| Multiple objects are valid | Singleton is unnecessary |
| Object has too much changing state | Can create bugs |
| You need easy unit testing | Singleton can be hard to mock |
| Dependency injection is available | DI is often cleaner |
| You are using it only for convenience | Can make design worse |

> **Beginner Warning:**  
> Singleton is useful, but overusing it can make your code tightly coupled and harder to test.

---

# 6. Advantages and Disadvantages

| Advantages | Disadvantages |
|---|---|
| Saves memory by reusing one object | Can create global state |
| Controls object creation | Can make unit testing harder |
| Provides common access point | Can hide dependencies |
| Useful for shared resources | Thread safety must be handled |
| Simple idea for interviews | Overuse makes code less flexible |

---

# 7. Singleton Implementations

> **Core rules of Singleton**
>
> - Make constructor `private`
> - Store instance in a `static` variable
> - Return object using `getInstance()`

---

<details>
<summary>1. Basic Singleton</summary>

## Basic Singleton

### Simple Explanation

This is the simplest version. The object is created inside the class and returned using `getInstance()`.

```java
class BasicSingleton {
    private static BasicSingleton instance = new BasicSingleton();

    private BasicSingleton() {
    }

    public static BasicSingleton getInstance() {
        return instance;
    }
}
```

### Pros

- Very easy to understand
- Good for learning the pattern
- Only one object is created

### Cons

- Object is created even if it is never used
- Not the best interview answer

### When To Use

Use it to understand the basic structure of Singleton.

</details>

---

<details>
<summary>2. Eager Singleton</summary>

## Eager Singleton

### Simple Explanation

The instance is created immediately when the class is loaded.

```java
class EagerSingleton {
    private static final EagerSingleton INSTANCE = new EagerSingleton();

    private EagerSingleton() {
    }

    public static EagerSingleton getInstance() {
        return INSTANCE;
    }
}
```

### Pros

- Simple
- Thread-safe
- No synchronization needed

### Cons

- Instance is created even if it is never used

### When To Use

Use when the object is small and definitely needed by the application.

</details>

---

<details>
<summary>3. Lazy Singleton</summary>

## Lazy Singleton

### Simple Explanation

The object is created only when `getInstance()` is called for the first time.

```java
class LazySingleton {
    private static LazySingleton instance;

    private LazySingleton() {
    }

    public static LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}
```

### Pros

- Object is created only when needed
- Saves memory if the object is never used
- Easy to write

### Cons

- Not thread-safe
- Two threads may create two objects at the same time

### When To Use

Use only in single-threaded applications or for learning.

> **Interview Trap:**  
> Lazy Singleton looks correct, but it is not safe in multithreaded code.

</details>

---

<details>
<summary>4. Thread-Safe Singleton using synchronized</summary>

## Thread-Safe Singleton

### Simple Explanation

The `synchronized` keyword allows only one thread at a time to enter `getInstance()`.

```java
class ThreadSafeSingleton {
    private static ThreadSafeSingleton instance;

    private ThreadSafeSingleton() {
    }

    public static synchronized ThreadSafeSingleton getInstance() {
        if (instance == null) {
            instance = new ThreadSafeSingleton();
        }
        return instance;
    }
}
```

### Pros

- Thread-safe
- Lazy initialization
- Easy to explain

### Cons

- Slower because every call to `getInstance()` is synchronized

### When To Use

Use when you need a simple thread-safe Singleton and performance is not a big concern.

</details>

---

<details open>
<summary>5. Bill Pugh Singleton Recommended</summary>

## Bill Pugh Singleton

### Simple Explanation

This approach uses a static inner class. The instance is created only when `getInstance()` is called.

```java
class BillPughSingleton {
    private BillPughSingleton() {
    }

    private static class Holder {
        private static final BillPughSingleton INSTANCE =
                new BillPughSingleton();
    }

    public static BillPughSingleton getInstance() {
        return Holder.INSTANCE;
    }
}
```

### Pros

- Thread-safe
- Lazy initialization
- No synchronization overhead
- Clean and interview-friendly

### Cons

- Slightly less obvious than eager initialization

### When To Use

Use this as the **recommended Java Singleton answer** in interviews.

> **Interview Tip:**  
> Say: "Bill Pugh Singleton is lazy, thread-safe, and avoids synchronization overhead."

</details>

---

<details>
<summary>6. Enum Singleton Brief Introduction</summary>

## Enum Singleton

### Simple Explanation

Java enum naturally creates only one instance for each enum value.

```java
enum EnumSingleton {
    INSTANCE;

    public void showMessage() {
        System.out.println("Hello from Singleton");
    }
}
```

Usage:

```java
EnumSingleton.INSTANCE.showMessage();
```

### Pros

- Very simple
- Thread-safe
- Good protection against serialization issues

### Cons

- Less flexible
- Cannot extend another class
- May look unusual to beginners

### When To Use

Use when you want the simplest safe Singleton style and enum fits your design.

</details>

---

# 8. Comparison Table

| Implementation | Initialization | Thread Safe | Performance | Best Use |
|---|---|---|---|---|
| Basic Singleton | Eager | Yes | Good | Learning basics |
| Eager Singleton | Eager | Yes | Good | Object always needed |
| Lazy Singleton | Lazy | No | Good | Single-threaded only |
| Synchronized Singleton | Lazy | Yes | Slower | Simple thread-safe code |
| Bill Pugh Singleton | Lazy | Yes | Excellent | Best interview answer |
| Enum Singleton | Eager-like | Yes | Excellent | Simple safe Singleton |

---

# 9. Notion Column Layout Idea

Use this as a 2-column revision section after importing into Notion:

| Remember | Avoid |
|---|---|
| Constructor should be `private` | Public constructor |
| Use `getInstance()` | Creating objects using `new` |
| Bill Pugh is recommended | Overusing Singleton |
| Lazy Singleton is not thread-safe | Storing too much mutable state |

---

# 10. Synced Block Content

> **Copy this into a Notion Synced Block:**  
> Singleton means **one class, one object, one global access point**.  
> Best interview implementation: **Bill Pugh Singleton**.

---

# 11. Practice Template

## Singleton Revision Template

- **Definition:** Singleton allows only one object of a class.
- **Why needed:** To share one controlled instance.
- **Real example:** Logger, config manager, cache manager.
- **Best code:** Bill Pugh Singleton.
- **Main risk:** Global state and difficult testing.

### Checklist

- [ ] I can define Singleton simply
- [ ] I can explain why constructor is private
- [ ] I can write Bill Pugh Singleton
- [ ] I can compare eager and lazy initialization
- [ ] I know why lazy Singleton is not thread-safe
- [ ] I can answer 5 interview questions

---

# 12. Common Interview Questions

| Question | Short Answer |
|---|---|
| What is Singleton Pattern? | A pattern that allows only one object of a class. |
| Why is the constructor private? | To stop outside classes from creating objects using `new`. |
| How do we access the Singleton object? | Through a public static `getInstance()` method. |
| What problem does Singleton solve? | It prevents multiple objects when only one shared object is needed. |
| Give one real use case. | Logger, configuration manager, or cache manager. |
| Is lazy Singleton thread-safe? | No, not by default. |
| How can Singleton be made thread-safe? | Use `synchronized`, Bill Pugh, or enum Singleton. |
| Which Singleton is recommended for interviews? | Bill Pugh Singleton. |
| What is eager initialization? | Object is created when the class loads. |
| What is one disadvantage of Singleton? | It can make testing harder because it behaves like global state. |

---

# 13. Revision Cheat Sheet

## Core Formula

```java
private constructor + static instance + getInstance()
```

## Best Interview Code

```java
class Singleton {
    private Singleton() {
    }

    private static class Holder {
        private static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstance() {
        return Holder.INSTANCE;
    }
}
```

## Key Takeaways

- Singleton is a **creational design pattern**
- It allows only **one object**
- It gives a **global access point**
- Constructor should be **private**
- Lazy Singleton is **not thread-safe**
- `synchronized` makes it safe but slower
- Bill Pugh is usually the best interview answer
- Enum Singleton is simple and safe, but less flexible

## Common Beginner Mistakes

- Making the constructor `public`
- Forgetting the `static` keyword
- Using lazy Singleton in multithreaded code
- Thinking Singleton means "make everything global"
- Overusing Singleton instead of proper dependency management

---

> **Final Interview Line:**  
> "Singleton ensures that only one instance of a class exists. In Java, the Bill Pugh approach is commonly recommended because it is lazy, thread-safe, and does not require synchronized access every time."
