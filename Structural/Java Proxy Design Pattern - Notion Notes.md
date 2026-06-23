# 🛡️ Java Proxy Design Pattern

> 💡 **Goal:** Master Proxy Pattern from scratch for Java interviews using simple explanations, visual notes, and easy Java code.

---

---

# 📌 1. What Is Proxy?

> 🧩 **Proxy is a structural design pattern that provides a substitute or placeholder for another object to control access to it.**

In simple words:

> **Client talks to proxy -> proxy decides how and when to forward the request to the real object**

Example idea:

```java
Image image = new ProxyImage("photo.jpg");
image.display();
```

The client uses the same interface, but the proxy adds control before reaching the real object.

---

# 🤔 2. Why Do We Need Proxy?

Proxy is useful when you do not want the client to access the real object directly every time.

Without Proxy:

```java
Image image = new RealImage("photo.jpg");
image.display();
```

The real object is created and used immediately.

With Proxy:

```java
Image image = new ProxyImage("photo.jpg");
image.display();
```

Now the proxy can delay creation, check permissions, cache results, or log access.

> 🚨 **Problem Proxy solves:**  
> It controls access to an object without changing the client-facing interface.

---

# 🏦 3. Real-Life Analogy

> 🏦 **Bank Representative Analogy**  
> You do not always go directly to the bank vault.  
> You talk to a bank representative first.  
> The representative checks identity, rules, and access before letting work happen.

In code:

- Vault = real object
- Bank representative = proxy
- Customer = client

> 💡 **Simple meaning:**  
> Proxy stands in front of the real object and manages access to it.

---

# 🎯 4. When To Use Proxy

| ✅ Use Proxy When | Example |
|---|---|
| Object creation is expensive | Lazy-loaded image |
| Access must be controlled | Security proxy |
| Calls should be logged | Logging proxy |
| Results should be cached | Remote service proxy |
| Remote object access should look local | Network proxy |

---

# 🚫 5. When NOT To Use Proxy

| ❌ Avoid Proxy When | Why |
|---|---|
| Direct access is already simple and safe | Proxy adds unnecessary code |
| There is no need to control access | Extra layer has no value |
| Performance overhead matters too much | Proxy adds one more step |
| Simpler design is enough | Can be overengineering |
| You actually want to add behavior, not control access | Decorator may fit better |

> ⚠️ **Beginner Warning:**  
> Proxy and Decorator look similar in structure, but their intent is different.

---

# ✅ 6. Advantages and Disadvantages

| ✅ Advantages | ❌ Disadvantages |
|---|---|
| Controls access to real object | Adds extra layer |
| Supports lazy initialization | Can increase complexity |
| Can add security, logging, caching | More classes to maintain |
| Client code remains unchanged | May add slight performance overhead |
| Useful for remote objects | Intent can be confused with Decorator |

---

# 🧠 7. Core Roles In Proxy

| Role | Meaning |
|---|---|
| `Subject` | Common interface for proxy and real object |
| `RealSubject` | The actual object doing the real work |
| `Proxy` | Controls access to the real object |
| `Client` | Uses the subject interface |

---

# 💻 8. Java Implementations

> 🧠 **Important Java interview point:**  
> Proxy and real object usually share the **same interface**, so client code does not need to change.

---

<details open>
<summary>🔽 1. Virtual Proxy Example (Lazy Loading)</summary>

## Virtual Proxy Example

This is the classic image loading example.

```java
interface Image {
    void display();
}

class RealImage implements Image {
    private final String fileName;

    public RealImage(String fileName) {
        this.fileName = fileName;
        loadFromDisk();
    }

    private void loadFromDisk() {
        System.out.println("Loading image from disk: " + fileName);
    }

    @Override
    public void display() {
        System.out.println("Displaying image: " + fileName);
    }
}

class ProxyImage implements Image {
    private final String fileName;
    private RealImage realImage;

    public ProxyImage(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void display() {
        if (realImage == null) {
            realImage = new RealImage(fileName);
        }
        realImage.display();
    }
}
```

Client code:

```java
public class Main {
    public static void main(String[] args) {
        Image image = new ProxyImage("photo.jpg");

        image.display();
        image.display();
    }
}
```

### ✅ Why this is useful

- Real object is created only when needed
- Saves time or memory if object is expensive
- Client still uses the same `Image` interface

</details>

---

<details>
<summary>🔽 2. Protection Proxy Example</summary>

## Protection Proxy Example

This proxy checks access before allowing the real action.

```java
interface Internet {
    void connect(String website);
}

class RealInternet implements Internet {
    @Override
    public void connect(String website) {
        System.out.println("Connecting to " + website);
    }
}

class ProxyInternet implements Internet {
    private final RealInternet realInternet = new RealInternet();

    @Override
    public void connect(String website) {
        if ("blocked.com".equalsIgnoreCase(website)) {
            System.out.println("Access denied to " + website);
            return;
        }
        realInternet.connect(website);
    }
}
```

Usage:

```java
public class Demo {
    public static void main(String[] args) {
        Internet internet = new ProxyInternet();

        internet.connect("google.com");
        internet.connect("blocked.com");
    }
}
```

### 📌 Key idea

The proxy checks rules before giving access to the real object.

</details>

---

<details>
<summary>🔽 3. Logging Proxy Example</summary>

## Logging Proxy Example

Proxy can also log requests before forwarding them.

```java
interface Service {
    void execute();
}

class RealService implements Service {
    @Override
    public void execute() {
        System.out.println("Real service executing");
    }
}

class LoggingProxy implements Service {
    private final RealService realService = new RealService();

    @Override
    public void execute() {
        System.out.println("Log: request received");
        realService.execute();
    }
}
```

Usage:

```java
public class Main {
    public static void main(String[] args) {
        Service service = new LoggingProxy();
        service.execute();
    }
}
```

</details>

---

# 📊 9. Common Types Of Proxy

| Proxy Type | Purpose |
|---|---|
| Virtual Proxy | Delays expensive object creation |
| Protection Proxy | Controls access using rules or permissions |
| Remote Proxy | Represents an object in another location |
| Caching Proxy | Stores results to reduce repeated work |
| Logging Proxy | Tracks requests and usage |

---

# 🔄 10. Flow Of Proxy

```text
Client -> Proxy -> RealSubject
```

Step by step:

1. Client calls method through the subject interface
2. Proxy receives the call first
3. Proxy checks rules, state, or conditions
4. Proxy forwards the request to the real object if needed

> 📌 **Memory hook:**  
> Same interface, controlled access.

---

# 🧱 11. Proxy vs Similar Patterns

| Pattern | Main Purpose |
|---|---|
| Proxy | Controls access to an object |
| Decorator | Adds behavior to an object |
| Adapter | Changes interface |
| Facade | Simplifies a subsystem |
| Observer | Notifies many objects about changes |

> ⚠️ **Most common confusion:**  
> Proxy and Decorator often look structurally similar.  
> Proxy is mainly about **control/access**.  
> Decorator is mainly about **extra behavior/features**.

---

# 🧱 12. Notion Column Layout Idea

Use this as a 2-column revision section after importing into Notion:

| 🧠 Remember | ⚠️ Avoid |
|---|---|
| Proxy controls access to real object | Confusing Proxy with Decorator |
| Same interface is preserved | Adding proxy when no control is needed |
| Great for lazy loading and security | Overengineering simple direct access |
| Real object may be created later | Forgetting proxy intent is access control |

---

# 🔁 13. Synced Block Content

> 📌 **Copy this into a Notion Synced Block:**  
> Proxy Pattern provides a placeholder for a real object and controls access to it.  
> Common uses include lazy loading, security checks, logging, and caching.

---

# 🧩 14. Practice Template

## Proxy Pattern Revision Template

- **Definition:** Proxy controls access to a real object using the same interface.
- **Main problem solved:** Direct access to the real object is expensive, unsafe, or needs control.
- **Real example:** Lazy image loader, internet access filter, remote service proxy.
- **Main rule:** Client talks to proxy first, not directly to real object.
- **Main benefit:** Controlled access without changing client code.

### ☑️ Checklist

- [ ] I can define Proxy simply
- [ ] I can explain Subject, RealSubject, and Proxy
- [ ] I can write a lazy loading proxy example
- [ ] I know different proxy types
- [ ] I can compare Proxy with Decorator
- [ ] I can give one practical Java use case

---

# 🎤 15. Common Interview Questions

| Question | Short Answer |
|---|---|
| What is Proxy Pattern? | A structural pattern that provides a placeholder for another object to control access to it. |
| Why do we use Proxy? | To add access control, lazy loading, logging, caching, or remote access handling. |
| What problem does Proxy solve? | Uncontrolled or expensive direct access to a real object. |
| Does Proxy change the interface? | No. Proxy usually keeps the same interface. |
| Proxy vs Decorator? | Proxy controls access; Decorator adds features. |
| What is a virtual proxy? | A proxy that delays creation of an expensive object until needed. |
| What is a protection proxy? | A proxy that checks access rules before forwarding requests. |
| Give one Java-like use case. | Lazy image loading or access-controlled service calls. |
| When should you avoid Proxy? | When direct access is already simple and safe. |
| What is the benefit to the client? | Client code can stay unchanged while control logic is added. |

---

# 📝 16. Revision Cheat Sheet

## 🧠 Core Formula

```java
Subject obj = new Proxy();
obj.request();
```

## ⭐ Memory Hook

> **Proxy = same interface, gatekeeper in front of the real object**

## ✅ Key Takeaways

- Proxy is a **structural design pattern**
- It provides a **placeholder/substitute**
- It controls **access to the real object**
- It usually keeps the **same interface**
- Common uses: **lazy loading, security, logging, caching**
- Client code usually does **not change**

## ⚠️ Common Beginner Mistakes

- Confusing Proxy with Decorator
- Thinking Proxy changes the interface
- Using Proxy without any real access-control need
- Forgetting that the main intent is control, not feature addition
- Adding too much unrelated logic into the proxy

---

> 🎯 **Final Interview Line:**  
> "Proxy Pattern provides a substitute object that controls access to the real object. It is commonly used for lazy initialization, security, logging, caching, and remote access while keeping the same interface for the client."
