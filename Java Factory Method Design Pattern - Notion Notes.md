# 🏭 Java Factory Method Design Pattern

> 💡 **Goal:** Master Factory Method from scratch for Java interviews with simple explanations, visual notes, and easy Java code.

---
---

# 📌 1. What Is Factory Method?

> 🧩 **Factory Method is a creational design pattern that provides a method for creating objects, instead of directly using `new` everywhere in the client code.**

In simple words:

> **Ask a factory for an object → factory decides which object to create**

Example idea:

```java
Notification notification = NotificationFactory.createNotification("EMAIL");
notification.notifyUser();
```

The client does not need to know the exact class creation details.

---

# 🤔 2. Why Do We Need Factory Method?

Factory Method helps when object creation becomes complex or depends on conditions.

Without Factory Method:

```java
if (type.equals("EMAIL")) {
    notification = new EmailNotification();
} else if (type.equals("SMS")) {
    notification = new SmsNotification();
} else if (type.equals("PUSH")) {
    notification = new PushNotification();
}
```

This logic may get repeated in many places.

With Factory Method:

```java
Notification notification =
        NotificationFactory.createNotification(type);
```

> 🚨 **Problem Factory Method solves:**  
> It moves object creation logic to one place, making code cleaner, flexible, and easier to maintain.

---

# 🚨 3. Why `new` Can Become a Problem

Using `new` is not bad. The problem starts when object creation logic spreads across the application.

| Problem With Too Much `new` | What Happens |
|---|---|
| Repeated creation logic | Same `if-else` appears everywhere |
| Tight coupling | Client depends on concrete classes |
| Hard to change | Adding new types requires many edits |
| Hard to test | Concrete dependencies are fixed |
| Violates Open/Closed Principle | Existing code changes for new object types |

> ⚠️ **Important:**  
> Factory Method does not remove `new`. It hides object creation inside a factory method.

---

# 🧩 4. Object Creation Without Exposing Instantiation Logic

Factory Method hides the exact object creation details from the client.

Client only knows the common interface:

```java
Notification notification =
        NotificationFactory.createNotification("EMAIL");
```

The factory knows the actual class:

```java
return new EmailNotification();
```

> 💡 **Simple meaning:**  
> The client asks for what it needs. The factory decides how to create it.

---

# 🔓 5. Open/Closed Principle

> 📌 **Open/Closed Principle:** Code should be **open for extension** but **closed for modification**.

Factory Method supports this by reducing changes in client code.

Example:

- Today: Email and SMS notifications
- Tomorrow: Add Push notification
- Client code should not need major changes
- Only factory creation logic may need updating in the simple version

> ⭐ **Interview Line:**  
> Factory Method helps follow the Open/Closed Principle by allowing new object types to be added with minimal changes to existing client code.

---

# 🔗 6. Loose Coupling

Without Factory Method:

```java
EmailNotification email = new EmailNotification();
```

Client is tightly coupled to `EmailNotification`.

With Factory Method:

```java
Notification notification =
        NotificationFactory.createNotification("EMAIL");
```

Client depends on the interface `Notification`, not the concrete class.

> ✅ **Loose coupling means:**  
> Code depends more on abstractions and less on specific implementation classes.

---

# 🏪 7. Real-Life Analogy

> 🏪 **Restaurant Analogy**  
> You order "pizza" from a restaurant.  
> You do not go into the kitchen and create it yourself.  
> The restaurant decides which chef, ingredients, and process to use.

In code:

- You are the client
- Restaurant is the factory
- Pizza is the object
- Kitchen logic is hidden creation logic

---

# 🎯 8. When To Use Factory Method

| ✅ Use Factory Method When | Example |
|---|---|
| Object type depends on input | Notification type |
| Object creation logic is repeated | Many places create same objects |
| Client should not know concrete classes | Payment processing |
| You want loose coupling | Interface-based design |
| You expect new types later | Email, SMS, Push, WhatsApp |

---

# 🚫 9. When NOT To Use Factory Method

| ❌ Avoid Factory Method When | Why |
|---|---|
| Object creation is very simple | Direct `new` is enough |
| Only one class is ever created | Factory adds extra code |
| No future variation is expected | Pattern may be overengineering |
| Code becomes harder to read | Simplicity matters |
| You are using it just to use a pattern | Design patterns should solve real problems |

> ⚠️ **Beginner Warning:**  
> Do not create factories for every class. Use them when object creation has meaningful variation or complexity.

---

# ✅ 10. Advantages and Disadvantages

| ✅ Advantages | ❌ Disadvantages |
|---|---|
| Hides object creation logic | Adds extra classes/methods |
| Reduces tight coupling | Can be overused |
| Improves maintainability | Simple code may become more complex |
| Supports interface-based design | Factory can become large |
| Helps with Open/Closed Principle | Wrong use can reduce clarity |

---

# 💻 11. Easy Java Implementation

> 🧠 **Scenario:** Create different notification objects: Email, SMS, and Push.

---

<details open>
<summary>🔽 Step 1: Create Product Interface</summary>

## Product Interface

All notification types follow the same contract.

```java
interface Notification {
    void notifyUser();
}
```

> 📌 **Why interface?**  
> The client can use `Notification` without depending on specific classes like `EmailNotification`.

</details>

---

<details>
<summary>🔽 Step 2: Create Concrete Products</summary>

## Concrete Classes

Each class provides its own implementation.

```java
class EmailNotification implements Notification {
    @Override
    public void notifyUser() {
        System.out.println("Sending email notification");
    }
}

class SmsNotification implements Notification {
    @Override
    public void notifyUser() {
        System.out.println("Sending SMS notification");
    }
}

class PushNotification implements Notification {
    @Override
    public void notifyUser() {
        System.out.println("Sending push notification");
    }
}
```

> 💡 **Simple idea:**  
> Different classes, same interface, different behavior.

</details>

---

<details>
<summary>🔽 Step 3: Create Factory Class</summary>

## Factory Class

The factory contains the object creation logic.

```java
class NotificationFactory {
    public static Notification createNotification(String type) {
        if (type == null) {
            return null;
        }

        if (type.equalsIgnoreCase("EMAIL")) {
            return new EmailNotification();
        }

        if (type.equalsIgnoreCase("SMS")) {
            return new SmsNotification();
        }

        if (type.equalsIgnoreCase("PUSH")) {
            return new PushNotification();
        }

        throw new IllegalArgumentException("Unknown notification type: " + type);
    }
}
```

> 🚨 **Note:**  
> `new` still exists, but it is now centralized inside the factory.

</details>

---

<details>
<summary>🔽 Step 4: Use Factory in Client Code</summary>

## Client Code

Client code asks the factory for an object.

```java
public class Main {
    public static void main(String[] args) {
        Notification notification =
                NotificationFactory.createNotification("EMAIL");

        notification.notifyUser();
    }
}
```

Output:

```text
Sending email notification
```

> ✅ **Benefit:**  
> Client code does not directly create `EmailNotification`.

</details>

---

# 🧱 12. Full Code Together

```java
interface Notification {
    void notifyUser();
}

class EmailNotification implements Notification {
    @Override
    public void notifyUser() {
        System.out.println("Sending email notification");
    }
}

class SmsNotification implements Notification {
    @Override
    public void notifyUser() {
        System.out.println("Sending SMS notification");
    }
}

class PushNotification implements Notification {
    @Override
    public void notifyUser() {
        System.out.println("Sending push notification");
    }
}

class NotificationFactory {
    public static Notification createNotification(String type) {
        if (type == null) {
            return null;
        }

        if (type.equalsIgnoreCase("EMAIL")) {
            return new EmailNotification();
        }

        if (type.equalsIgnoreCase("SMS")) {
            return new SmsNotification();
        }

        if (type.equalsIgnoreCase("PUSH")) {
            return new PushNotification();
        }

        throw new IllegalArgumentException("Unknown notification type: " + type);
    }
}

public class Main {
    public static void main(String[] args) {
        Notification notification =
                NotificationFactory.createNotification("EMAIL");

        notification.notifyUser();
    }
}
```

---

# 📊 13. Comparison: Without vs With Factory

| Topic | Without Factory | With Factory Method |
|---|---|---|
| Object creation | Client uses `new` | Factory creates object |
| Coupling | High | Low |
| Code repetition | More likely | Reduced |
| Maintainability | Harder as types grow | Easier |
| Client knowledge | Knows concrete classes | Knows interface |
| Flexibility | Lower | Higher |

---

# 🧱 14. Notion Column Layout Idea

Use this as a 2-column section after importing into Notion:

| 🧠 Remember | ⚠️ Avoid |
|---|---|
| Factory hides object creation | Making factories for every class |
| Client depends on interface | Repeating `new` logic everywhere |
| Useful when object type varies | Overengineering simple code |
| Helps loose coupling | Large messy factory methods |

---

# 🔁 15. Synced Block Content

> 📌 **Copy this into a Notion Synced Block:**  
> Factory Method centralizes object creation.  
> Client asks for an object through a method and depends on an interface, not concrete classes.

---

# 🧩 16. Practice Template

## Factory Method Revision Template

- **Definition:** Factory Method creates objects without exposing creation logic to the client.
- **Main problem solved:** Too much direct `new` and tight coupling.
- **Real example:** Notification factory, payment factory, document factory.
- **Main principle:** Program to an interface, not an implementation.
- **Interview phrase:** Hides instantiation logic and supports loose coupling.

### ☑️ Checklist

- [ ] I can define Factory Method simply
- [ ] I can explain why too much `new` is a problem
- [ ] I can write product interface and concrete classes
- [ ] I can write a simple factory method
- [ ] I can explain loose coupling
- [ ] I can connect it with Open/Closed Principle

---

# 🎤 17. Common Interview Questions

| Question | Short Answer |
|---|---|
| What is Factory Method Pattern? | A creational pattern that creates objects through a method instead of direct client-side `new`. |
| Why do we use Factory Method? | To hide object creation logic and reduce tight coupling. |
| Does Factory Method remove `new` completely? | No. It centralizes `new` inside the factory. |
| What problem happens with too much `new`? | Client code becomes tightly coupled to concrete classes. |
| What is the product in Factory Method? | The common interface or parent type returned by the factory. |
| What is a concrete product? | The actual class created, like `EmailNotification`. |
| How does Factory Method support loose coupling? | Client depends on an interface instead of specific classes. |
| How does it relate to Open/Closed Principle? | New types can be added with less change to client code. |
| When should you avoid Factory Method? | When object creation is simple and unlikely to change. |
| Give a real use case. | Notification creation, payment method creation, or document parser creation. |

---

# 📝 18. Revision Cheat Sheet

## 🧠 Core Formula

```java
Interface variable = Factory.create(type);
```

## ⭐ Memory Hook

> **Do not ask the client to cook. Let the factory kitchen create the object.**

## ✅ Key Takeaways

- Factory Method is a **creational design pattern**
- It creates objects through a method
- It hides object creation logic
- It reduces direct use of `new` in client code
- It improves loose coupling
- It works well with interfaces
- It helps keep client code cleaner
- It is useful when object type depends on input or conditions

## ⚠️ Common Beginner Mistakes

- Thinking Factory Method means `new` disappears
- Creating factories for very simple objects
- Returning concrete classes instead of interfaces
- Putting too much unrelated logic inside the factory
- Forgetting why the pattern is needed

---

> 🎯 **Final Interview Line:**  
> "Factory Method is used to create objects without exposing instantiation logic to the client. It centralizes object creation, reduces coupling, and helps client code depend on abstractions instead of concrete classes."
