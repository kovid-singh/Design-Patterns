# 🧬 Java Prototype Design Pattern

> 💡 **Goal:** Master the Prototype Pattern from scratch for Java interviews using simple explanations, visual notes, and easy Java code.

---

## 📖 Table of Contents

- 📌 What is Prototype?
- 🤔 Why do we need it?
- 🧩 Problem it solves
- 📄 Real-life analogy
- 🎯 When to use / avoid
- ✅ Advantages and ❌ disadvantages
- 💻 Java implementations
- 📊 Shallow copy vs Deep copy
- 🎤 Interview questions
- 📝 Revision cheat sheet

---

# 📌 1. What Is Prototype?

> 🧩 **Prototype is a creational design pattern used to create new objects by copying an existing object instead of creating them from scratch.**

In simple words:

> **Make a copy of an existing object when creating a new one is expensive or repetitive.**

Example idea:

```java
Document copy = originalDocument.clone();
```

The existing object is called the **prototype**.

---

# 🤔 2. Why Do We Need Prototype?

Sometimes creating an object is expensive because it needs:

- Database calls
- Network calls
- Large configuration
- Complex calculations
- Many default values

Without Prototype:

```java
Document document = new Document();
document.setTitle("Report");
document.setFont("Arial");
document.setPageSize("A4");
document.loadImages();
document.loadStyles();
```

If we need many similar documents, repeating this setup is wasteful.

With Prototype:

```java
Document copy = originalDocument.clone();
```

> 🚨 **Problem Prototype solves:**  
> It avoids expensive repeated object creation by copying an already prepared object.

---

# 🧩 3. Core Idea

Prototype has one main idea:

```java
new object = copy existing object
```

Instead of asking a class to build everything again, we ask an existing object to copy itself.

> 🟨 **Highlight:** Prototype is useful when the object already has the state/configuration you want.

---

# 📄 4. Real-Life Analogy

> 📄 **Photocopy Analogy**  
> If you need 20 copies of a filled form, you do not write the same form again and again.  
> You make photocopies of the original.

In code:

- Original form = prototype object
- Photocopy = cloned object
- Copy machine = clone method

---

# 🎯 5. When To Use Prototype

| ✅ Use Prototype When | Example |
|---|---|
| Object creation is expensive | Object loads data or configuration |
| Many objects are similar | Game characters, documents |
| You want to avoid repeated setup | Preconfigured templates |
| Runtime object type is unknown | Clone object through interface |
| You need copies with small changes | Resume template, report template |

---

# 🚫 6. When NOT To Use Prototype

| ❌ Avoid Prototype When | Why |
|---|---|
| Object creation is simple | Direct `new` is easier |
| Object has many shared mutable references | Copying may cause bugs |
| Deep copy is complicated | Clone logic becomes hard |
| Object should not be copied | Security or identity issue |
| You do not need similar objects | Pattern adds no value |

> ⚠️ **Beginner Warning:**  
> Prototype becomes tricky when objects contain other objects. You must know whether you need a **shallow copy** or a **deep copy**.

---

# ✅ 7. Advantages and Disadvantages

| ✅ Advantages | ❌ Disadvantages |
|---|---|
| Faster than creating complex objects from scratch | Clone logic can be confusing |
| Reduces repeated setup code | Deep copy can be difficult |
| Useful for preconfigured objects | Shared references can cause bugs |
| Helps create similar objects quickly | Java `Cloneable` is not very beginner-friendly |
| Can reduce dependency on concrete classes | Not needed for simple objects |

---

# 💻 8. Java Implementations

> 🧠 **Scenario:** Create copies of documents and employees.

---

<details open>
<summary>🔽 1. Basic Prototype Using Cloneable</summary>

## Basic Prototype

The class implements `Cloneable` and overrides `clone()`.

```java
class Document implements Cloneable {
    private String title;
    private String content;

    public Document(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public Document clone() {
        try {
            return (Document) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void show() {
        System.out.println(title + ": " + content);
    }
}
```

Usage:

```java
Document original = new Document("Report", "Monthly sales data");
Document copy = original.clone();

copy.show();
```

### ✅ Pros

- Simple
- Fast for basic objects
- Common interview example

### ❌ Cons

- Uses Java `Cloneable`, which can feel awkward
- Gives shallow copy by default

</details>

---

<details>
<summary>🔽 2. Shallow Copy Example</summary>

## Shallow Copy

A shallow copy copies primitive fields but shares referenced objects.

```java
class Address {
    String city;

    Address(String city) {
        this.city = city;
    }
}

class Employee implements Cloneable {
    String name;
    Address address;

    Employee(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    @Override
    public Employee clone() {
        try {
            return (Employee) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
```

Usage:

```java
Address address = new Address("Delhi");
Employee e1 = new Employee("Rahul", address);
Employee e2 = e1.clone();

e2.address.city = "Mumbai";

System.out.println(e1.address.city); // Mumbai
```

> 🚨 **Why Mumbai?**  
> Both employees share the same `Address` object.

### ✅ Pros

- Easy and fast
- Fine when shared references are safe

### ❌ Cons

- Changes in copied object can affect original object
- Dangerous with mutable nested objects

</details>

---

<details>
<summary>🔽 3. Deep Copy Example</summary>

## Deep Copy

A deep copy also creates new copies of nested objects.

```java
class Address {
    String city;

    Address(String city) {
        this.city = city;
    }

    Address(Address other) {
        this.city = other.city;
    }
}

class Employee implements Cloneable {
    String name;
    Address address;

    Employee(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    @Override
    public Employee clone() {
        try {
            Employee copy = (Employee) super.clone();
            copy.address = new Address(this.address);
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
```

Usage:

```java
Employee e1 = new Employee("Rahul", new Address("Delhi"));
Employee e2 = e1.clone();

e2.address.city = "Mumbai";

System.out.println(e1.address.city); // Delhi
```

> ✅ **Why Delhi?**  
> The copied employee has a separate `Address` object.

### ✅ Pros

- Safer for mutable nested objects
- Original and copy are independent

### ❌ Cons

- More code
- Can become complex for large object graphs

</details>

---

<details>
<summary>🔽 4. Copy Constructor Alternative</summary>

## Copy Constructor

Many Java developers prefer copy constructors because they are easier to understand than `Cloneable`.

```java
class Student {
    private String name;
    private int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Student(Student other) {
        this.name = other.name;
        this.age = other.age;
    }
}
```

Usage:

```java
Student s1 = new Student("Ananya", 22);
Student s2 = new Student(s1);
```

### ✅ Pros

- Very readable
- No `Cloneable` needed
- Easy for interviews

### ❌ Cons

- Client must know the concrete class
- Less flexible when copying through an interface

</details>

---

<details>
<summary>🔽 5. Prototype Registry</summary>

## Prototype Registry

A registry stores ready-made prototype objects and returns copies when needed.

```java
import java.util.HashMap;
import java.util.Map;

interface Shape extends Cloneable {
    Shape clone();
    void draw();
}

class Circle implements Shape {
    private String color;

    public Circle(String color) {
        this.color = color;
    }

    @Override
    public Shape clone() {
        return new Circle(this.color);
    }

    @Override
    public void draw() {
        System.out.println("Drawing " + color + " circle");
    }
}

class ShapeRegistry {
    private Map<String, Shape> shapes = new HashMap<>();

    public void addShape(String key, Shape shape) {
        shapes.put(key, shape);
    }

    public Shape getShape(String key) {
        return shapes.get(key).clone();
    }
}
```

Usage:

```java
ShapeRegistry registry = new ShapeRegistry();
registry.addShape("redCircle", new Circle("red"));

Shape circleCopy = registry.getShape("redCircle");
circleCopy.draw();
```

### ✅ Pros

- Useful for preconfigured objects
- Client does not create concrete objects repeatedly
- Good for game objects, templates, and UI components

### ❌ Cons

- Registry must be managed carefully
- Clone behavior must be correct

</details>

---

# 📊 9. Shallow Copy vs Deep Copy

| Topic | Shallow Copy | Deep Copy |
|---|---|---|
| Primitive fields | Copied | Copied |
| Referenced objects | Shared | Copied separately |
| Speed | Faster | Slower |
| Safety | Risky with mutable objects | Safer |
| Example issue | Original changes when copy changes nested object | Original stays independent |
| Best use | Immutable or safe shared references | Mutable nested objects |

---

# 📊 10. Prototype Comparison Table

| Implementation | Main Idea | Best Use | Risk |
|---|---|---|---|
| `Cloneable` Prototype | Override `clone()` | Basic interview example | Shallow copy by default |
| Shallow Copy | Copy top-level object only | Simple objects | Shared nested state |
| Deep Copy | Copy nested objects too | Mutable nested objects | More code |
| Copy Constructor | Create object from another object | Readable Java code | Needs concrete class |
| Prototype Registry | Store and clone prebuilt objects | Templates and game objects | Registry management |

---

# 🧱 11. Notion Column Layout Idea

Use this as a 2-column revision section after importing into Notion:

| 🧠 Remember | ⚠️ Avoid |
|---|---|
| Prototype creates objects by copying | Copying objects blindly |
| Good for expensive object creation | Ignoring shallow vs deep copy |
| Existing object is the prototype | Sharing mutable nested objects accidentally |
| Registry stores ready prototypes | Using Prototype for simple objects |

---

# 🔁 12. Synced Block Content

> 📌 **Copy this into a Notion Synced Block:**  
> Prototype Pattern creates new objects by copying existing objects.  
> Always check whether you need a shallow copy or a deep copy.

---

# 🧩 13. Practice Template

## Prototype Pattern Revision Template

- **Definition:** Creates new objects by copying existing objects.
- **Main problem solved:** Expensive or repetitive object creation.
- **Real example:** Photocopy, resume template, game character template.
- **Main warning:** Understand shallow copy vs deep copy.
- **Interview phrase:** Clone an existing configured object instead of building from scratch.

### ☑️ Checklist

- [ ] I can define Prototype simply
- [ ] I can explain why copying may be faster than creating
- [ ] I know shallow copy vs deep copy
- [ ] I can write a basic `clone()` example
- [ ] I can explain copy constructor alternative
- [ ] I can explain prototype registry

---

# 🎤 14. Common Interview Questions

| Question | Short Answer |
|---|---|
| What is Prototype Pattern? | A creational pattern that creates new objects by copying existing objects. |
| Why do we use Prototype? | To avoid expensive or repeated object creation. |
| What is a prototype object? | The existing object that is copied to create new objects. |
| What is shallow copy? | A copy where nested object references are shared. |
| What is deep copy? | A copy where nested objects are also copied. |
| What is the risk of shallow copy? | Changing nested objects in the copy may affect the original. |
| How is Prototype implemented in Java? | Commonly using `Cloneable`, `clone()`, copy constructor, or registry. |
| What is a prototype registry? | A map/cache that stores prototype objects and returns their copies. |
| When should Prototype be avoided? | When object creation is simple or copying is unsafe. |
| Give a real use case. | Copying game characters, document templates, or configured objects. |

---

# 📝 15. Revision Cheat Sheet

## 🧠 Core Formula

```java
NewObject copy = existingObject.clone();
```

## ⭐ Memory Hook

> **Prototype = photocopy an object instead of creating it from scratch**

## ✅ Key Takeaways

- Prototype is a **creational design pattern**
- It creates objects by copying existing objects
- It is useful when object creation is expensive
- It works well for templates and preconfigured objects
- Shallow copy shares nested objects
- Deep copy copies nested objects too
- Copy constructors are a clean Java-friendly alternative
- Prototype registry stores ready-made objects and returns copies

## ⚠️ Common Beginner Mistakes

- Thinking clone always means deep copy
- Forgetting that shallow copy shares object references
- Using Prototype when direct `new` is simpler
- Not handling nested mutable objects
- Making clone logic too complex

---

> 🎯 **Final Interview Line:**  
> "Prototype Pattern is used when we want to create new objects by copying existing objects. It is helpful when object creation is expensive, but we must be careful about shallow and deep copies."
