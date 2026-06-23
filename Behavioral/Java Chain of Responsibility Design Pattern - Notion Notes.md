# ⛓️ Java Chain of Responsibility Design Pattern

> 💡 **Goal:** Master Chain of Responsibility Pattern from scratch for Java interviews using simple explanations, visual notes, and easy Java code.

---

---

# 📌 1. What Is Chain of Responsibility?

> 🧩 **Chain of Responsibility is a behavioral design pattern where a request is passed through a chain of handlers until one of them handles it.**

In simple words:

> **Request moves from one object to the next until someone handles it**

Example idea:

```java
Handler chain = new Manager();
chain.setNext(new Director());
chain.handleRequest(12000);
```

The client sends the request once, and the chain decides who should process it.

---

# 🤔 2. Why Do We Need Chain of Responsibility?

Chain of Responsibility is useful when multiple objects may be able to handle a request, but the sender should not decide which one manually.

Without Chain of Responsibility:

```java
if (amount <= 1000) {
    teamLead.approve(amount);
} else if (amount <= 10000) {
    manager.approve(amount);
} else {
    director.approve(amount);
}
```

The sender must know all handlers and their decision rules.

With Chain of Responsibility:

```java
chain.handleRequest(12000);
```

> 🚨 **Problem Chain of Responsibility solves:**  
> It decouples the sender from the exact handler by passing the request through a chain.

---

# 📄 3. Real-Life Analogy

> 📄 **Office Approval Analogy**  
> Suppose a leave request or expense request goes through:
>
> - Team Lead
> - Manager
> - Director
>
> If one person cannot approve it, the request moves to the next person.

In code:

- Request = leave or expense request
- Employees = handlers
- Passing upward = chain

> 💡 **Simple meaning:**  
> One handler tries, otherwise forwards to the next.

---

# 🎯 4. When To Use Chain of Responsibility

| ✅ Use Chain of Responsibility When | Example |
|---|---|
| Many objects can handle the same request | Approval system |
| Sender should not know the exact handler | Support ticket routing |
| Request should move step by step | Logging levels |
| Handlers should be loosely coupled | Authentication filters |
| Order of processing matters | Web request pipeline |

---

# 🚫 5. When NOT To Use Chain of Responsibility

| ❌ Avoid Chain of Responsibility When | Why |
|---|---|
| Only one fixed handler exists | Direct call is simpler |
| The sender already clearly knows the target handler | Chain adds no value |
| Request routing rules are trivial | Extra classes are unnecessary |
| Long chains reduce performance or clarity | Harder to trace |
| You need all handlers to act, not just one | Another pattern may fit better |

> ⚠️ **Beginner Warning:**  
> Chain of Responsibility is usually about passing a request until it is handled, not necessarily making every handler do the same work.

---

# ✅ 6. Advantages and Disadvantages

| ✅ Advantages | ❌ Disadvantages |
|---|---|
| Decouples sender from handler | Request may go unhandled |
| Easy to add or reorder handlers | Debugging long chains can be harder |
| Flexible request routing | Chain order must be managed carefully |
| Supports Open/Closed Principle | Too many handlers can add complexity |
| Good for layered processing | Performance may suffer with long chains |

---

# 🧠 7. Core Roles In Chain of Responsibility

| Role | Meaning |
|---|---|
| `Handler` | Common interface or abstract class for handling requests |
| `ConcreteHandler` | Specific handler that processes or forwards the request |
| `Client` | Sends request to the first handler in the chain |

---

# 💻 8. Java Implementations

> 🧠 **Important Java interview point:**  
> Each handler usually knows only the **next handler**, not the full chain.

---

<details open>
<summary>🔽 1. Basic Approval Chain Example</summary>

## Approval System Example

This is the easiest interview-friendly example.

```java
abstract class Approver {
    protected Approver nextApprover;

    public void setNext(Approver nextApprover) {
        this.nextApprover = nextApprover;
    }

    public abstract void approveLeave(int days);
}

class TeamLead extends Approver {
    @Override
    public void approveLeave(int days) {
        if (days <= 2) {
            System.out.println("Team Lead approved " + days + " day(s) leave");
        } else if (nextApprover != null) {
            nextApprover.approveLeave(days);
        }
    }
}

class Manager extends Approver {
    @Override
    public void approveLeave(int days) {
        if (days <= 5) {
            System.out.println("Manager approved " + days + " day(s) leave");
        } else if (nextApprover != null) {
            nextApprover.approveLeave(days);
        }
    }
}

class Director extends Approver {
    @Override
    public void approveLeave(int days) {
        if (days <= 10) {
            System.out.println("Director approved " + days + " day(s) leave");
        } else {
            System.out.println("Leave request denied");
        }
    }
}
```

Client code:

```java
public class Main {
    public static void main(String[] args) {
        Approver teamLead = new TeamLead();
        Approver manager = new Manager();
        Approver director = new Director();

        teamLead.setNext(manager);
        manager.setNext(director);

        teamLead.approveLeave(1);
        teamLead.approveLeave(4);
        teamLead.approveLeave(8);
        teamLead.approveLeave(12);
    }
}
```

### ✅ Why this is useful

- Client sends request once
- Correct handler is chosen by chain flow
- New approvers can be added easily

</details>

---

<details>
<summary>🔽 2. Logging Chain Example</summary>

## Logging Level Example

Chain of Responsibility is often explained using log levels.

```java
abstract class Logger {
    public static final int INFO = 1;
    public static final int DEBUG = 2;
    public static final int ERROR = 3;

    protected int level;
    protected Logger nextLogger;

    public void setNextLogger(Logger nextLogger) {
        this.nextLogger = nextLogger;
    }

    public void logMessage(int level, String message) {
        if (this.level <= level) {
            write(message);
        }
        if (nextLogger != null) {
            nextLogger.logMessage(level, message);
        }
    }

    protected abstract void write(String message);
}

class InfoLogger extends Logger {
    public InfoLogger(int level) {
        this.level = level;
    }

    @Override
    protected void write(String message) {
        System.out.println("INFO: " + message);
    }
}

class ErrorLogger extends Logger {
    public ErrorLogger(int level) {
        this.level = level;
    }

    @Override
    protected void write(String message) {
        System.out.println("ERROR: " + message);
    }
}
```

Usage:

```java
public class Demo {
    public static void main(String[] args) {
        Logger infoLogger = new InfoLogger(Logger.INFO);
        Logger errorLogger = new ErrorLogger(Logger.ERROR);

        infoLogger.setNextLogger(errorLogger);

        infoLogger.logMessage(Logger.INFO, "Application started");
        infoLogger.logMessage(Logger.ERROR, "Something failed");
    }
}
```

### 📌 Key idea

This version shows that sometimes more than one handler may react depending on design.

</details>

---

<details>
<summary>🔽 3. Support Ticket Routing Example</summary>

## Support Ticket Example

This is a practical business example.

```java
abstract class SupportHandler {
    protected SupportHandler next;

    public void setNext(SupportHandler next) {
        this.next = next;
    }

    public abstract void handle(String issueType);
}

class BasicSupport extends SupportHandler {
    @Override
    public void handle(String issueType) {
        if ("password".equalsIgnoreCase(issueType)) {
            System.out.println("Basic support handled password issue");
        } else if (next != null) {
            next.handle(issueType);
        }
    }
}

class TechnicalSupport extends SupportHandler {
    @Override
    public void handle(String issueType) {
        if ("server".equalsIgnoreCase(issueType)) {
            System.out.println("Technical support handled server issue");
        } else if (next != null) {
            next.handle(issueType);
        }
    }
}

class BillingSupport extends SupportHandler {
    @Override
    public void handle(String issueType) {
        if ("billing".equalsIgnoreCase(issueType)) {
            System.out.println("Billing support handled billing issue");
        } else {
            System.out.println("No handler found for issue: " + issueType);
        }
    }
}
```

Usage:

```java
public class Main {
    public static void main(String[] args) {
        SupportHandler basic = new BasicSupport();
        SupportHandler technical = new TechnicalSupport();
        SupportHandler billing = new BillingSupport();

        basic.setNext(technical);
        technical.setNext(billing);

        basic.handle("password");
        basic.handle("server");
        basic.handle("billing");
        basic.handle("network");
    }
}
```

### ✅ Why mention this in interviews

- Shows practical request routing
- Easy to explain loose coupling and chain flow

</details>

---

# 📊 9. Direct Routing vs Chain of Responsibility

| Topic | Direct Routing | Chain of Responsibility |
|---|---|---|
| Sender knows handler | Yes | No |
| Flexibility | Lower | Higher |
| Adding handlers | Harder | Easier |
| Coupling | Higher | Lower |
| Request flow | Fixed in sender | Managed by chain |

---

# 🔄 10. Flow Of Chain of Responsibility

```text
Client -> Handler 1 -> Handler 2 -> Handler 3
```

Step by step:

1. Client sends request to first handler
2. First handler checks if it can process it
3. If yes, it handles the request
4. If not, it forwards to the next handler
5. This continues until handled or chain ends

> 📌 **Memory hook:**  
> Try here, else pass ahead.

---

# 🧱 11. Chain of Responsibility vs Similar Patterns

| Pattern | Main Purpose |
|---|---|
| Chain of Responsibility | Passes request through handlers until handled |
| Command | Wraps a request as an object |
| Strategy | Switches algorithms |
| Observer | Notifies many dependents |
| Mediator | Centralizes communication |

> ⚠️ **Most common confusion:**  
> Chain of Responsibility decides **who handles the request**.  
> Command decides **how a request is wrapped and triggered**.

---

# 🧱 12. Notion Column Layout Idea

Use this as a 2-column revision section after importing into Notion:

| 🧠 Remember | ⚠️ Avoid |
|---|---|
| Request moves through a chain of handlers | Confusing it with Command |
| Sender does not need exact handler knowledge | Building unnecessarily long chains |
| Great for approval and routing systems | Forgetting fallback when nobody handles |
| Handlers are loosely coupled | Making handlers depend on full chain details |

---

# 🔁 13. Synced Block Content

> 📌 **Copy this into a Notion Synced Block:**  
> Chain of Responsibility Pattern passes a request through a sequence of handlers until one handles it.  
> It is useful when the sender should not know which object will process the request.

---

# 🧩 14. Practice Template

## Chain of Responsibility Pattern Revision Template

- **Definition:** Chain of Responsibility passes a request through multiple handlers until one handles it.
- **Main problem solved:** Sender should not be tightly coupled to a specific handler.
- **Real example:** Leave approval, support ticket routing, logging chain, filters.
- **Main rule:** Each handler either handles the request or forwards it.
- **Main benefit:** Flexible and decoupled request routing.

### ☑️ Checklist

- [ ] I can define Chain of Responsibility simply
- [ ] I can explain handler and next handler roles
- [ ] I can write an approval chain example
- [ ] I know when request may go unhandled
- [ ] I can compare it with Command
- [ ] I can give one real-world routing example

---

# 🎤 15. Common Interview Questions

| Question | Short Answer |
|---|---|
| What is Chain of Responsibility Pattern? | A behavioral pattern where a request passes through handlers until one handles it. |
| Why do we use Chain of Responsibility? | To decouple the sender from the exact handler. |
| What problem does it solve? | Hardcoded request routing and tight coupling to handlers. |
| What does each handler do? | It either handles the request or forwards it to the next handler. |
| Can a request go unhandled? | Yes, if no handler in the chain processes it. |
| Chain of Responsibility vs Command? | Chain decides who handles; Command wraps the request as an object. |
| What is a real Java-like use case? | Servlet filters, security filters, logging chains, approval workflows. |
| Can more than one handler act? | Yes, depending on design, like logging chains. |
| When should you avoid it? | When direct routing is simpler or only one handler exists. |
| What is one risk of this pattern? | Long chains can be harder to debug and maintain. |

---

# 📝 16. Revision Cheat Sheet

## 🧠 Core Formula

```java
handler1.setNext(handler2);
handler2.setNext(handler3);
handler1.handle(request);
```

## ⭐ Memory Hook

> **Chain of Responsibility = pass the request until someone handles it**

## ✅ Key Takeaways

- Chain of Responsibility is a **behavioral design pattern**
- It passes a **request through multiple handlers**
- Sender does not need to know the exact handler
- Each handler can **handle or forward**
- Great for **approvals, filters, routing, logging**
- Chain order is important

## ⚠️ Common Beginner Mistakes

- Confusing it with Command
- Forgetting to set the next handler
- Not handling the case where nobody processes the request
- Building chains that are too long or unclear
- Assuming every handler must always process the request

---

> 🎯 **Final Interview Line:**  
> "Chain of Responsibility Pattern lets a request move through a sequence of handlers until one of them handles it. It reduces coupling between the sender and the receiver and is useful in approval flows, filters, routing pipelines, and logging systems."
