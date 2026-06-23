# 🎮 Java Command Design Pattern

> 💡 **Goal:** Master Command Pattern from scratch for Java interviews using simple explanations, visual notes, and easy Java code.

---

---

# 📌 1. What Is Command?

> 🧩 **Command is a behavioral design pattern that turns a request into an object, so it can be passed, stored, queued, logged, or undone.**

In simple words:

> **Wrap an action inside an object**

Example idea:

```java
Command lightOn = new LightOnCommand(new Light());
RemoteControl remote = new RemoteControl();
remote.setCommand(lightOn);
remote.pressButton();
```

The client does not directly call the receiver's method. It sends a command object instead.

---

# 🤔 2. Why Do We Need Command?

Command is useful when you want to separate:

- the object that requests an action
- from the object that actually performs it

Without Command:

```java
light.turnOn();
fan.start();
door.open();
```

The caller must know each exact object and method.

With Command:

```java
remote.pressButton();
```

The remote only knows it has a command to execute.

> 🚨 **Problem Command solves:**  
> It decouples the sender of a request from the object that performs the request.

---

# 🎮 3. Real-Life Analogy

> 🎮 **TV Remote Analogy**  
> When you press a remote button, the remote does not directly become the TV.  
> It sends a specific command like "turn on", "volume up", or "change channel".

In code:

- Remote = invoker
- TV = receiver
- Button action = command object

> 💡 **Simple meaning:**  
> Command turns an action into an object.

---

# 🎯 4. When To Use Command

| ✅ Use Command When | Example |
|---|---|
| You want to decouple request sender and receiver | Remote control |
| Actions need undo/redo support | Text editor |
| Requests should be queued | Job scheduling |
| Commands should be logged or stored | Transaction history |
| Different actions should be treated uniformly | Menu items, buttons |

---

# 🚫 5. When NOT To Use Command

| ❌ Avoid Command When | Why |
|---|---|
| Action is very simple and direct | Pattern adds extra classes |
| No need for queue, log, undo, or decoupling | Direct method call is enough |
| Design becomes too class-heavy | Can reduce readability |
| There are very few fixed actions | Simpler design may be better |
| Small utility logic does not need object form | Overengineering risk |

> ⚠️ **Beginner Warning:**  
> Command is powerful, but do not use it just to replace every method call with a class.

---

# ✅ 6. Advantages and Disadvantages

| ✅ Advantages | ❌ Disadvantages |
|---|---|
| Decouples sender and receiver | Creates more classes |
| Supports undo/redo | Can feel verbose |
| Requests can be queued or logged | Extra abstraction for simple actions |
| Easy to add new commands | Codebase may become class-heavy |
| Good for extensible action systems | Can be overused |

---

# 🧠 7. Core Roles In Command

| Role | Meaning |
|---|---|
| `Command` | Interface for executing a request |
| `ConcreteCommand` | Specific command implementation |
| `Receiver` | Object that performs the real work |
| `Invoker` | Object that triggers the command |
| `Client` | Creates command and connects invoker with receiver |

---

# 💻 8. Java Implementations

> 🧠 **Important Java interview point:**  
> Command is often used when actions must be **stored**, **queued**, or **undone**.

---

<details open>
<summary>🔽 1. Basic Command Example (Light Remote)</summary>

## Light Remote Example

This is the classic Command example.

```java
interface Command {
    void execute();
}

class Light {
    public void turnOn() {
        System.out.println("Light is ON");
    }

    public void turnOff() {
        System.out.println("Light is OFF");
    }
}

class LightOnCommand implements Command {
    private final Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOn();
    }
}

class LightOffCommand implements Command {
    private final Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOff();
    }
}

class RemoteControl {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void pressButton() {
        command.execute();
    }
}
```

Client code:

```java
public class Main {
    public static void main(String[] args) {
        Light light = new Light();

        Command lightOn = new LightOnCommand(light);
        Command lightOff = new LightOffCommand(light);

        RemoteControl remote = new RemoteControl();

        remote.setCommand(lightOn);
        remote.pressButton();

        remote.setCommand(lightOff);
        remote.pressButton();
    }
}
```

### ✅ Why this is useful

- Remote is independent of light details
- New commands can be added easily
- Same invoker can run different actions

</details>

---

<details>
<summary>🔽 2. Command With Undo</summary>

## Undo Example

Command pattern is very common when undo is needed.

```java
interface Command {
    void execute();
    void undo();
}

class Fan {
    public void start() {
        System.out.println("Fan started");
    }

    public void stop() {
        System.out.println("Fan stopped");
    }
}

class FanOnCommand implements Command {
    private final Fan fan;

    public FanOnCommand(Fan fan) {
        this.fan = fan;
    }

    @Override
    public void execute() {
        fan.start();
    }

    @Override
    public void undo() {
        fan.stop();
    }
}

class Remote {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void press() {
        command.execute();
    }

    public void pressUndo() {
        command.undo();
    }
}
```

Usage:

```java
public class Demo {
    public static void main(String[] args) {
        Fan fan = new Fan();
        Command fanOn = new FanOnCommand(fan);

        Remote remote = new Remote();
        remote.setCommand(fanOn);

        remote.press();
        remote.pressUndo();
    }
}
```

### 📌 Key idea

Because the action is stored in an object, reversing it becomes easier.

</details>

---

<details>
<summary>🔽 3. Command Queue Example</summary>

## Queue Example

Commands can be stored and executed later.

```java
import java.util.LinkedList;
import java.util.Queue;

interface TaskCommand {
    void execute();
}

class EmailTask implements TaskCommand {
    @Override
    public void execute() {
        System.out.println("Sending email task");
    }
}

class SmsTask implements TaskCommand {
    @Override
    public void execute() {
        System.out.println("Sending SMS task");
    }
}

class TaskQueue {
    private final Queue<TaskCommand> queue = new LinkedList<>();

    public void addCommand(TaskCommand command) {
        queue.offer(command);
    }

    public void processCommands() {
        while (!queue.isEmpty()) {
            queue.poll().execute();
        }
    }
}
```

Usage:

```java
public class Main {
    public static void main(String[] args) {
        TaskQueue queue = new TaskQueue();
        queue.addCommand(new EmailTask());
        queue.addCommand(new SmsTask());

        queue.processCommands();
    }
}
```

### ✅ Why mention this in interviews

- Shows how Command helps with scheduling and queuing
- Connects the pattern to job systems and asynchronous processing

</details>

---

# 📊 9. Direct Call vs Command Pattern

| Topic | Direct Method Call | Command Pattern |
|---|---|---|
| Coupling | Higher | Lower |
| Undo support | Harder | Easier |
| Queuing/logging | Limited | Easy |
| Flexibility | Lower | Higher |
| Extensibility | Lower | Higher |

---

# 🔄 10. Flow Of Command

```text
Client -> Command -> Invoker -> Receiver
```

Step by step:

1. Client creates receiver
2. Client creates command object with receiver
3. Client gives command to invoker
4. Invoker triggers command
5. Command calls receiver to perform actual work

> 📌 **Memory hook:**  
> Request becomes an object.

---

# 🧱 11. Command vs Similar Patterns

| Pattern | Main Purpose |
|---|---|
| Command | Encapsulates a request as an object |
| Strategy | Encapsulates interchangeable algorithms |
| Observer | Notifies many objects about change |
| State | Changes behavior based on state |
| Chain of Responsibility | Passes request through handlers |

> ⚠️ **Most common confusion:**  
> Command is about **wrapping a request/action**.  
> Strategy is about **choosing an algorithm**.

---

# 🧱 12. Notion Column Layout Idea

Use this as a 2-column revision section after importing into Notion:

| 🧠 Remember | ⚠️ Avoid |
|---|---|
| Command wraps actions in objects | Using it for every tiny direct call |
| Great for undo, queue, log | Confusing it with Strategy |
| Sender and receiver are decoupled | Adding too many unnecessary command classes |
| Invoker does not need receiver details | Overengineering simple actions |

---

# 🔁 13. Synced Block Content

> 📌 **Copy this into a Notion Synced Block:**  
> Command Pattern turns a request into an object.  
> This makes it easy to queue, log, store, or undo actions while keeping sender and receiver loosely coupled.

---

# 🧩 14. Practice Template

## Command Pattern Revision Template

- **Definition:** Command encapsulates a request as an object.
- **Main problem solved:** Tight coupling between request sender and receiver.
- **Real example:** TV remote, menu action, text editor undo, job queue.
- **Main rule:** Invoker triggers commands, command calls receiver.
- **Main benefit:** Decoupled and extensible action handling.

### ☑️ Checklist

- [ ] I can define Command simply
- [ ] I can explain Command, Receiver, and Invoker
- [ ] I can write a light remote example
- [ ] I know how undo works with Command
- [ ] I can explain queue usage
- [ ] I can compare Command with Strategy

---

# 🎤 15. Common Interview Questions

| Question | Short Answer |
|---|---|
| What is Command Pattern? | A behavioral pattern that encapsulates a request as an object. |
| Why do we use Command? | To decouple sender and receiver and support undo, queue, log, or store actions. |
| What problem does Command solve? | Tight coupling between action request and action execution. |
| What is the receiver? | The object that performs the real work. |
| What is the invoker? | The object that triggers the command. |
| Command vs Strategy? | Command wraps a request; Strategy wraps an algorithm. |
| Can Command support undo? | Yes. That is one of its most common uses. |
| Give one real Java-like use case. | GUI menu actions, job queues, or editor undo operations. |
| When should you avoid Command? | When actions are very simple and do not need decoupling or extra control. |
| What is the benefit of treating requests as objects? | They can be stored, logged, queued, and reused. |

---

# 📝 16. Revision Cheat Sheet

## 🧠 Core Formula

```java
Command command = new ConcreteCommand(receiver);
invoker.setCommand(command);
invoker.execute();
```

## ⭐ Memory Hook

> **Command = convert action into an object**

## ✅ Key Takeaways

- Command is a **behavioral design pattern**
- It wraps a **request as an object**
- It decouples **sender and receiver**
- Great for **undo, queue, logging, scheduling**
- Invoker triggers command
- Receiver performs real work

## ⚠️ Common Beginner Mistakes

- Confusing Command with Strategy
- Using Command for very simple direct calls
- Forgetting the difference between invoker and receiver
- Creating too many command classes without real need
- Missing undo logic when interview asks for it

---

> 🎯 **Final Interview Line:**  
> "Command Pattern encapsulates a request as an object so the sender does not need to know how the action is performed. It is useful for undo/redo, queuing, logging, and decoupling invokers from receivers."
