# 🎁 Java Decorator Design Pattern

> 💡 **Goal:** Master Decorator Pattern from scratch for Java interviews using simple explanations, visual notes, and easy Java code.

---

---

# 📌 1. What Is Decorator?

> 🧩 **Decorator is a structural design pattern that lets you add new behavior to an object dynamically without changing its original class.**

In simple words:

> **Take an object -> wrap it -> add extra behavior**

Example idea:

```java
Coffee coffee = new MilkDecorator(new SugarDecorator(new BasicCoffee()));
System.out.println(coffee.getDescription());
```

The original object stays the same, but new features are added by wrapping it with decorator objects.

---

# 🤔 2. Why Do We Need Decorator?

Decorator is useful when you want to add features to objects without creating many subclasses.

Without Decorator:

```java
class MilkCoffee extends Coffee { }
class SugarCoffee extends Coffee { }
class MilkSugarCoffee extends Coffee { }
class MilkSugarWhipCoffee extends Coffee { }
```

This grows very fast and becomes messy.

With Decorator:

```java
Coffee coffee = new WhipDecorator(
        new SugarDecorator(
                new MilkDecorator(new BasicCoffee())));
```

> 🚨 **Problem Decorator solves:**  
> It adds responsibilities to objects flexibly without subclass explosion.

---

# ☕ 3. Real-Life Analogy

> ☕ **Coffee Toppings Analogy**  
> Start with plain coffee.  
> Then add milk.  
> Then sugar.  
> Then whipped cream.

The base coffee remains coffee, but each wrapper adds something extra.

In code:

- Plain coffee = original object
- Milk = decorator
- Sugar = decorator
- Whip = decorator
- Final drink = object with stacked extra behavior

> 💡 **Simple meaning:**  
> Decorator is like adding layers around an object to give it more features.

---

# 🎯 4. When To Use Decorator

| ✅ Use Decorator When | Example |
|---|---|
| You want to add features at runtime | Coffee toppings |
| Too many subclasses would be needed | Scrollable + bordered + colored window |
| You want flexible combinations of behavior | Notifications with SMS + email + logging |
| You want to keep the same interface | Input stream wrappers |
| Behavior should be layered step by step | Compression + encryption |

---

# 🚫 5. When NOT To Use Decorator

| ❌ Avoid Decorator When | Why |
|---|---|
| Behavior is simple and fixed | A direct class may be enough |
| Too many wrappers will confuse the design | Harder to trace behavior |
| Inheritance already solves the need cleanly | Decorator adds unnecessary layers |
| You need to change the interface itself | Adapter may fit better |
| Order of wrappers becomes difficult to manage | Can reduce readability |

> ⚠️ **Beginner Warning:**  
> Decorator adds behavior, but too many layers can make debugging harder.

---

# ✅ 6. Advantages and Disadvantages

| ✅ Advantages | ❌ Disadvantages |
|---|---|
| Adds behavior dynamically | Many small classes may be created |
| Avoids subclass explosion | Can be harder to debug |
| Follows Open/Closed Principle | Wrapper order can matter |
| Keeps same interface | Design may look complex at first |
| Supports flexible combinations | Too many decorators can reduce clarity |

---

# 🧠 7. Core Roles In Decorator

| Role | Meaning |
|---|---|
| `Component` | Common interface for original object and decorators |
| `ConcreteComponent` | The base object being decorated |
| `Decorator` | Abstract wrapper that follows the same interface |
| `ConcreteDecorator` | Adds extra behavior |
| `Client` | Uses the object through the component interface |

---

# 💻 8. Java Implementations

> 🧠 **Important Java interview point:**  
> Decorator works by wrapping an object that has the **same interface**.

---

<details open>
<summary>🔽 1. Basic Decorator Example (Coffee)</summary>

## Coffee Decorator Example

This is the classic and easiest way to understand Decorator.

```java
interface Coffee {
    String getDescription();
    double getCost();
}

class BasicCoffee implements Coffee {
    @Override
    public String getDescription() {
        return "Basic Coffee";
    }

    @Override
    public double getCost() {
        return 50.0;
    }
}

abstract class CoffeeDecorator implements Coffee {
    protected final Coffee coffee;

    public CoffeeDecorator(Coffee coffee) {
        this.coffee = coffee;
    }
}

class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return coffee.getDescription() + ", Milk";
    }

    @Override
    public double getCost() {
        return coffee.getCost() + 10.0;
    }
}

class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return coffee.getDescription() + ", Sugar";
    }

    @Override
    public double getCost() {
        return coffee.getCost() + 5.0;
    }
}
```

Client code:

```java
public class Main {
    public static void main(String[] args) {
        Coffee coffee = new SugarDecorator(
                new MilkDecorator(new BasicCoffee()));

        System.out.println(coffee.getDescription());
        System.out.println(coffee.getCost());
    }
}
```

Output idea:

```text
Basic Coffee, Milk, Sugar
65.0
```

### ✅ Why this is useful

- Base object stays unchanged
- Features can be stacked
- Easy to extend with new decorators

</details>

---

<details>
<summary>🔽 2. Decorator With Multiple Features</summary>

## Adding More Decorators

You can keep extending behavior without modifying old classes.

```java
class WhipDecorator extends CoffeeDecorator {
    public WhipDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return coffee.getDescription() + ", Whip";
    }

    @Override
    public double getCost() {
        return coffee.getCost() + 15.0;
    }
}
```

Usage:

```java
Coffee coffee = new WhipDecorator(
        new SugarDecorator(
                new MilkDecorator(new BasicCoffee())));

System.out.println(coffee.getDescription());
System.out.println(coffee.getCost());
```

### 📌 Key idea

Each new decorator adds one feature while keeping the same `Coffee` interface.

</details>

---

<details>
<summary>🔽 3. Real Java Library Example</summary>

## Java I/O Example

Decorator is heavily used in Java I/O.

```java
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

public class Demo {
    public static void main(String[] args) throws Exception {
        InputStream input =
                new BufferedInputStream(new FileInputStream("data.txt"));

        System.out.println("Decorator in Java I/O created");
    }
}
```

### Why this is Decorator

- `InputStream` is the common component
- `FileInputStream` is the concrete component
- `BufferedInputStream` adds extra behavior
- Interface stays compatible

> 📌 **Interview Tip:**  
> Mention `BufferedInputStream`, `DataInputStream`, and `BufferedReader` as Java Decorator examples.

</details>

---

# 📊 9. Inheritance vs Decorator

| Topic | Inheritance | Decorator |
|---|---|---|
| Add behavior | By subclassing | By wrapping objects |
| Runtime flexibility | Low | High |
| Number of classes | Can explode quickly | More controlled combinations |
| Reuse | Fixed at compile time | Dynamic and composable |
| Interview preference for flexible add-ons | Limited | Strong |

---

# 🔄 10. Flow Of Decorator

```text
Client -> Decorator -> Decorator -> ConcreteComponent
```

Step by step:

1. Client uses the component interface
2. A decorator wraps the base object
3. Another decorator can wrap the previous decorator
4. Each layer adds behavior before or after delegating

> 📌 **Memory hook:**  
> Same interface, extra layers.

---

# 🧱 11. Decorator vs Similar Patterns

| Pattern | Main Purpose |
|---|---|
| Decorator | Adds behavior while keeping the same interface |
| Adapter | Changes one interface into another |
| Facade | Simplifies a subsystem |
| Proxy | Controls access to an object |
| Strategy | Changes algorithm/behavior by composition |

> ⚠️ **Most common confusion:**  
> Decorator keeps the **same interface**.  
> Adapter changes the **interface**.

---

# 🧱 12. Notion Column Layout Idea

Use this as a 2-column revision section after importing into Notion:

| 🧠 Remember | ⚠️ Avoid |
|---|---|
| Decorator adds behavior dynamically | Confusing it with Adapter |
| Same interface is preserved | Creating too many unclear layers |
| Great alternative to many subclasses | Using it for fixed simple behavior |
| Wrappers can be stacked | Forgetting wrapper order may matter |

---

# 🔁 13. Synced Block Content

> 📌 **Copy this into a Notion Synced Block:**  
> Decorator Pattern adds new behavior by wrapping an object with classes that keep the same interface.  
> It is useful when inheritance would create too many subclasses.

---

# 🧩 14. Practice Template

## Decorator Pattern Revision Template

- **Definition:** Decorator adds behavior dynamically by wrapping an object.
- **Main problem solved:** Too many subclasses for feature combinations.
- **Real example:** Coffee toppings, Java I/O streams, notification add-ons.
- **Main rule:** Decorators keep the same interface as the original object.
- **Main benefit:** Flexible layered behavior.

### ☑️ Checklist

- [ ] I can define Decorator simply
- [ ] I can explain Component and Decorator roles
- [ ] I can write a coffee decorator example
- [ ] I know why Decorator is better than subclass explosion
- [ ] I can compare Decorator with Adapter
- [ ] I can give a Java I/O example

---

# 🎤 15. Common Interview Questions

| Question | Short Answer |
|---|---|
| What is Decorator Pattern? | A structural pattern that adds behavior to objects dynamically by wrapping them. |
| Why do we use Decorator? | To add features without changing the original class or creating many subclasses. |
| What problem does Decorator solve? | Subclass explosion and inflexible feature addition. |
| Does Decorator change the interface? | No. It keeps the same interface. |
| Decorator vs Adapter? | Decorator adds behavior; Adapter changes interface. |
| Decorator vs inheritance? | Decorator is more flexible because behavior can be added at runtime. |
| What is the component? | The common interface used by both base object and decorators. |
| Give one real Java example. | `BufferedInputStream` wrapping `FileInputStream`. |
| When should you avoid Decorator? | When behavior is simple, fixed, or too many wrappers would reduce clarity. |
| What principle does Decorator support? | Open/Closed Principle. |

---

# 📝 16. Revision Cheat Sheet

## 🧠 Core Formula

```java
Component obj = new ConcreteDecorator(new ConcreteComponent());
```

## ⭐ Memory Hook

> **Decorator = same object, extra features, wrapped in layers**

## ✅ Key Takeaways

- Decorator is a **structural design pattern**
- It adds behavior **dynamically**
- It keeps the **same interface**
- It uses **composition**
- It avoids **subclass explosion**
- Wrappers can be stacked in layers
- Common Java example: **I/O streams**

## ⚠️ Common Beginner Mistakes

- Confusing Decorator with Adapter
- Thinking Decorator changes the interface
- Creating too many wrappers without clarity
- Forgetting that order of decorators can matter
- Using inheritance when runtime flexibility is needed

---

> 🎯 **Final Interview Line:**  
> "Decorator Pattern is used to add new behavior to an object dynamically by wrapping it inside decorator classes that keep the same interface. It is useful when inheritance would create too many subclasses and when features need to be combined flexibly at runtime."
