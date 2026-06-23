# 🔌 Java Adapter Design Pattern

> 💡 **Goal:** Master Adapter from scratch for Java interviews using simple explanations, visual notes, and easy Java code.

---

---

# 📌 1. What Is Adapter?

> 🧩 **Adapter is a structural design pattern that allows two incompatible interfaces to work together.**

In simple words:

> **Client expects one format -> existing class gives another -> Adapter translates between them**

Example idea:

```java
MediaPlayer player = new MediaAdapter(new AdvancedMediaPlayer());
player.play("mp4", "video.mp4");
```

The client keeps using the interface it understands, while the adapter connects it to the old or third-party class.

---

# 🤔 2. Why Do We Need Adapter?

Adapter is useful when you already have working code, but its method names or interface do not match what your new code expects.

Without Adapter:

```java
AdvancedMediaPlayer player = new AdvancedMediaPlayer();
player.playMp4("video.mp4");
```

Now the client must know the exact class and exact method names.

With Adapter:

```java
MediaPlayer player = new MediaAdapter(new AdvancedMediaPlayer());
player.play("mp4", "video.mp4");
```

> 🚨 **Problem Adapter solves:**  
> It connects classes that are useful but incompatible at the interface level.

---

# 🔌 3. Real-Life Analogy

> 🔋 **Mobile Charger Analogy**  
> Your charger works, and the wall socket works, but their plug shapes are different.  
> You use a travel adapter to make them fit together.

In code:

- Charger = existing class
- Wall socket = client expectation
- Travel adapter = adapter class
- Electricity still flows = real work still happens

> 💡 **Simple meaning:**  
> Adapter does not change the original device. It just makes communication possible.

---

# 🎯 4. When To Use Adapter

| ✅ Use Adapter When | Example |
|---|---|
| Existing class works but has wrong interface | Legacy logger |
| You are integrating a third-party library | Payment SDK |
| Client should not depend on old API details | Media player wrapper |
| You want to reuse old code safely | Old reporting service |
| Multiple systems need one common interface | Unified notification API |

---

# 🚫 5. When NOT To Use Adapter

| ❌ Avoid Adapter When | Why |
|---|---|
| Interfaces are already compatible | No adapter is needed |
| You can easily refactor the class directly | Extra layer is unnecessary |
| You need major behavior changes, not just translation | Another pattern may fit better |
| The design becomes full of tiny wrappers | Can hurt readability |
| Simpler code is enough | Pattern may be overengineering |

> ⚠️ **Beginner Warning:**  
> Adapter should solve a real mismatch. Do not use it just because a design pattern is available.

---

# ✅ 6. Advantages and Disadvantages

| ✅ Advantages | ❌ Disadvantages |
|---|---|
| Reuses existing code | Adds an extra layer |
| Avoids changing tested legacy code | Can make tracing harder |
| Improves compatibility | Too many adapters can clutter design |
| Hides conversion logic from client | Sometimes direct refactor is simpler |
| Helps integrate third-party APIs | Can hide poor design if overused |

---

# 🧠 7. Core Roles In Adapter

| Role | Meaning |
|---|---|
| `Target` | The interface the client wants |
| `Adaptee` | The existing incompatible class |
| `Adapter` | Converts calls from `Target` to `Adaptee` |
| `Client` | Uses only the `Target` interface |

---

# 💻 8. Java Implementations

> 🧠 **Important Java interview point:**  
> **Object Adapter** is the preferred answer in Java because it uses composition.

---

<details open>
<summary>🔽 1. Object Adapter (Recommended)</summary>

## Object Adapter

This version stores the adaptee as an object and forwards calls to it.

```java
interface MediaPlayer {
    void play(String audioType, String fileName);
}

class AdvancedMediaPlayer {
    public void playVlc(String fileName) {
        System.out.println("Playing VLC file: " + fileName);
    }

    public void playMp4(String fileName) {
        System.out.println("Playing MP4 file: " + fileName);
    }
}

class MediaAdapter implements MediaPlayer {
    private final AdvancedMediaPlayer advancedPlayer;

    public MediaAdapter(AdvancedMediaPlayer advancedPlayer) {
        this.advancedPlayer = advancedPlayer;
    }

    @Override
    public void play(String audioType, String fileName) {
        if ("vlc".equalsIgnoreCase(audioType)) {
            advancedPlayer.playVlc(fileName);
        } else if ("mp4".equalsIgnoreCase(audioType)) {
            advancedPlayer.playMp4(fileName);
        } else {
            System.out.println("Unsupported format: " + audioType);
        }
    }
}
```

Client code:

```java
public class Main {
    public static void main(String[] args) {
        MediaPlayer player = new MediaAdapter(new AdvancedMediaPlayer());

        player.play("vlc", "movie.vlc");
        player.play("mp4", "video.mp4");
    }
}
```

### ✅ Why this is preferred

- Uses composition
- Flexible and clean
- Works well in Java
- Best answer for interviews

</details>

---

<details>
<summary>🔽 2. Class Adapter (Conceptual)</summary>

## Class Adapter

This version uses inheritance. In Java, it is less flexible because Java does not support multiple class inheritance.

```java
interface Printer {
    void print();
}

class LegacyPrinter {
    public void printOldFormat() {
        System.out.println("Printing using legacy printer");
    }
}

class PrinterAdapter extends LegacyPrinter implements Printer {
    @Override
    public void print() {
        printOldFormat();
    }
}
```

Client code:

```java
public class Demo {
    public static void main(String[] args) {
        Printer printer = new PrinterAdapter();
        printer.print();
    }
}
```

### ⚠️ Important note

- Uses inheritance
- Less flexible than object adapter
- Mention it in interviews, but prefer object adapter in Java

</details>

---

<details>
<summary>🔽 3. Tiny Payment Adapter Example</summary>

## Payment Adapter Example

This is a small real-world interview example.

```java
interface PaymentProcessor {
    void pay(int amount);
}

class OldPaymentGateway {
    public void makePayment(int rupees) {
        System.out.println("Paid: " + rupees);
    }
}

class PaymentAdapter implements PaymentProcessor {
    private final OldPaymentGateway gateway;

    public PaymentAdapter(OldPaymentGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void pay(int amount) {
        gateway.makePayment(amount);
    }
}
```

Usage:

```java
public class Main {
    public static void main(String[] args) {
        PaymentProcessor processor =
                new PaymentAdapter(new OldPaymentGateway());

        processor.pay(500);
    }
}
```

</details>

---

# 📊 9. Object Adapter vs Class Adapter

| Topic | Object Adapter | Class Adapter |
|---|---|---|
| Technique | Composition | Inheritance |
| Flexibility | High | Lower |
| Java friendliness | Excellent | Limited |
| Reuse | Can wrap different objects | Tied to one class hierarchy |
| Interview preference | Recommended | Mention as concept |

---

# 🔄 10. Flow Of Adapter

```text
Client -> Target interface -> Adapter -> Adaptee
```

Step by step:

1. Client calls the method it knows
2. Adapter receives that request
3. Adapter translates the call
4. Adaptee performs the real work

> 📌 **Memory hook:**  
> Client talks to Adapter. Adapter talks to old code.

---

# 🧱 11. Adapter vs Similar Patterns

| Pattern | Main Purpose |
|---|---|
| Adapter | Makes incompatible interfaces work together |
| Decorator | Adds behavior without changing interface |
| Facade | Gives a simpler interface to a subsystem |
| Bridge | Separates abstraction from implementation |
| Proxy | Controls access to an object |

> ⚠️ **Most common confusion:**  
> Adapter changes the **interface**.  
> Decorator keeps the **same interface** and adds behavior.

---

# 🧱 12. Notion Column Layout Idea

Use this as a 2-column revision section after importing into Notion:

| 🧠 Remember | ⚠️ Avoid |
|---|---|
| Adapter solves interface mismatch | Using it when no mismatch exists |
| Object adapter is best in Java | Overengineering tiny problems |
| Client depends on target interface | Letting client know legacy API details |
| Good for legacy and third-party code | Adding many unnecessary wrappers |

---

# 🔁 13. Synced Block Content

> 📌 **Copy this into a Notion Synced Block:**  
> Adapter Pattern works like a translator between incompatible interfaces.  
> In Java, object adapter using composition is the recommended approach.

---

# 🧩 14. Practice Template

## Adapter Pattern Revision Template

- **Definition:** Adapter makes incompatible interfaces work together.
- **Main problem solved:** Interface mismatch.
- **Real example:** Charger plug adapter, payment gateway wrapper, legacy logger.
- **Best Java style:** Object adapter using composition.
- **Main benefit:** Reuse existing code without changing it.

### ☑️ Checklist

- [ ] I can define Adapter simply
- [ ] I can explain Target, Adaptee, and Adapter
- [ ] I can write object adapter code
- [ ] I know why object adapter is preferred in Java
- [ ] I can compare Adapter with Decorator and Facade
- [ ] I can give one practical example

---

# 🎤 15. Common Interview Questions

| Question | Short Answer |
|---|---|
| What is Adapter Pattern? | A structural pattern that makes incompatible interfaces work together. |
| Why do we use Adapter? | To reuse existing code when the interface does not match client expectations. |
| What problem does Adapter solve? | Interface incompatibility. |
| What is the Target in Adapter? | The interface expected by the client. |
| What is the Adaptee? | The existing class with a different interface. |
| Object adapter vs class adapter? | Object adapter uses composition; class adapter uses inheritance. |
| Which adapter is preferred in Java? | Object adapter. |
| How is Adapter different from Decorator? | Adapter changes interface; Decorator adds behavior. |
| Give one real use case. | Wrapping a third-party payment or media library. |
| When should you avoid Adapter? | When there is no interface mismatch or direct refactoring is simpler. |

---

# 📝 16. Revision Cheat Sheet

## 🧠 Core Formula

```java
Target target = new Adapter(new Adaptee());
```

## ⭐ Memory Hook

> **Adapter = translator between old code and new expectations**

## ✅ Key Takeaways

- Adapter is a **structural design pattern**
- It solves **interface mismatch**
- Client uses `Target`
- Existing class is `Adaptee`
- Adapter translates calls between them
- In Java, **object adapter** is preferred
- Great for **legacy code** and **third-party APIs**

## ⚠️ Common Beginner Mistakes

- Confusing Adapter with Decorator
- Using Adapter when direct refactoring is simpler
- Forgetting that Adapter is mainly about interface conversion
- Making client depend directly on the adaptee
- Overusing wrappers and adding unnecessary complexity

---

> 🎯 **Final Interview Line:**  
> "Adapter Pattern is used when two classes need to work together but their interfaces are incompatible. In Java, the usual approach is object adapter, where the adapter implements the target interface and internally delegates calls to the existing class."
