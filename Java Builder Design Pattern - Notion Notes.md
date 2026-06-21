# 🧱 Java Builder Design Pattern

> 💡 **Goal:** Master the Builder Pattern from scratch for Java interviews using simple explanations, visual notes, and easy Java code.



# 📌 1. What Is Builder?

> 🧩 **Builder is a creational design pattern used to create complex objects step by step.**

In simple words:

> **Instead of passing many values into a huge constructor, use a builder to set only what you need.**

Example:

```java
User user = new User.Builder("Rahul", "rahul@email.com")
        .age(25)
        .city("Delhi")
        .build();
```

The object creation becomes readable and flexible.

---

# 🤔 2. Why Do We Need Builder?

Builder is useful when a class has:

- Many fields
- Optional fields
- Complex object creation
- Need for readable code
- Need for immutable objects

Without Builder:

```java
User user = new User("Rahul", "rahul@email.com", 25, "Delhi", true, false);
```

This is confusing because you may forget what each value means.

With Builder:

```java
User user = new User.Builder("Rahul", "rahul@email.com")
        .age(25)
        .city("Delhi")
        .isActive(true)
        .build();
```

> 🚨 **Problem Builder solves:**  
> It avoids confusing constructors and makes complex object creation readable.

---

# 🚨 3. Problem With Too Many Constructors

This problem is called the **telescoping constructor problem**.

```java
class User {
    public User(String name) {
    }

    public User(String name, String email) {
    }

    public User(String name, String email, int age) {
    }

    public User(String name, String email, int age, String city) {
    }
}
```

As fields increase, constructors become harder to manage.

| Problem | Why It Hurts |
|---|---|
| Too many constructors | Code becomes messy |
| Many optional fields | Hard to choose correct constructor |
| Same data types | Easy to pass values in wrong order |
| Low readability | Hard to understand object creation |
| Hard maintenance | Adding fields creates more constructors |

> ⚠️ **Beginner Warning:**  
> If a constructor has many parameters, especially optional ones, Builder may be a better choice.

---

# 🏗️ 4. Real-Life Analogy

> 🍔 **Burger Order Analogy**  
> When ordering a burger, you choose step by step:
>
> - Bread
> - Patty
> - Cheese
> - Sauce
> - Extra toppings
>
> You do not pass everything at once in a confusing line.

In code:

- Burger = final object
- BurgerBuilder = builder
- Toppings = optional fields
- `build()` = final order creation

---

# 🎯 5. When To Use Builder

| ✅ Use Builder When | Example |
|---|---|
| Object has many fields | User profile |
| Many fields are optional | Address, age, preferences |
| Constructor becomes confusing | `new User(a, b, c, d, e)` |
| You want readable object creation | Fluent builder methods |
| You want immutable objects | `final` fields with no setters |

---

# 🚫 6. When NOT To Use Builder

| ❌ Avoid Builder When | Why |
|---|---|
| Object has only 2-3 simple fields | Constructor is enough |
| Object creation is not complex | Builder adds extra code |
| Fields change frequently after creation | Setters may be more suitable |
| You do not need readability gains | Pattern may be overengineering |
| Class is very small | Extra builder class is unnecessary |

> 💡 **Simple rule:**  
> Use Builder when object creation needs clarity, flexibility, or many optional values.

---

# ✅ 7. Advantages and Disadvantages

| ✅ Advantages | ❌ Disadvantages |
|---|---|
| Improves readability | Adds extra code |
| Handles optional fields well | More classes or inner classes |
| Avoids large constructors | May be overused |
| Supports immutable objects | Slightly more verbose |
| Object creation is step-by-step | Not needed for simple objects |

---

# 💻 8. Java Implementations

> 🧠 **Scenario:** Create a `User` object with required fields and optional fields.

---

<details open>
<summary>🔽 1. Problem Code: Constructor With Many Parameters</summary>

## Constructor Approach

This works, but it becomes difficult to read when parameters increase.

```java
class User {
    private String name;
    private String email;
    private int age;
    private String city;
    private boolean active;

    public User(String name, String email, int age, String city, boolean active) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.city = city;
        this.active = active;
    }
}
```

Client code:

```java
User user = new User("Rahul", "rahul@email.com", 25, "Delhi", true);
```

### ❌ Problem

- Hard to remember parameter order
- Optional fields still need values
- Code is less readable

</details>

---

<details>
<summary>🔽 2. Basic Builder Implementation</summary>

## Basic Builder

This version uses a separate builder class.

```java
class User {
    private String name;
    private String email;
    private int age;
    private String city;

    public User(String name, String email, int age, String city) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.city = city;
    }
}

class UserBuilder {
    private String name;
    private String email;
    private int age;
    private String city;

    public UserBuilder name(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder age(int age) {
        this.age = age;
        return this;
    }

    public UserBuilder city(String city) {
        this.city = city;
        return this;
    }

    public User build() {
        return new User(name, email, age, city);
    }
}
```

Usage:

```java
User user = new UserBuilder()
        .name("Rahul")
        .email("rahul@email.com")
        .age(25)
        .city("Delhi")
        .build();
```

### ✅ Pros

- Easy to read
- Flexible
- Good for learning

### ❌ Cons

- `User` constructor is still public
- Separate builder class adds extra code

</details>

---

<details>
<summary>🔽 3. Static Inner Builder Recommended</summary>

## Static Inner Builder

This is the most common Java interview style.

```java
class User {
    private final String name;
    private final String email;
    private final int age;
    private final String city;
    private final boolean active;

    private User(Builder builder) {
        this.name = builder.name;
        this.email = builder.email;
        this.age = builder.age;
        this.city = builder.city;
        this.active = builder.active;
    }

    public static class Builder {
        private final String name;
        private final String email;
        private int age;
        private String city;
        private boolean active;

        public Builder(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder active(boolean active) {
            this.active = active;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
```

Usage:

```java
User user = new User.Builder("Rahul", "rahul@email.com")
        .age(25)
        .city("Delhi")
        .active(true)
        .build();
```

### ✅ Pros

- Very readable
- Handles optional fields well
- Can create immutable objects
- Common interview answer

### ❌ Cons

- More code than constructor
- Not necessary for simple classes

### 🎯 When To Use

Use when a class has required fields plus many optional fields.

> ⭐ **Interview Tip:**  
> Static inner Builder is the most common Java Builder implementation.

</details>

---

<details>
<summary>🔽 4. Builder With Validation</summary>

## Builder With Validation

You can validate values inside `build()`.

```java
class Product {
    private final String name;
    private final double price;

    private Product(Builder builder) {
        this.name = builder.name;
        this.price = builder.price;
    }

    public static class Builder {
        private final String name;
        private double price;

        public Builder(String name) {
            this.name = name;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Product build() {
            if (price < 0) {
                throw new IllegalArgumentException("Price cannot be negative");
            }
            return new Product(this);
        }
    }
}
```

Usage:

```java
Product product = new Product.Builder("Laptop")
        .price(75000)
        .build();
```

### ✅ Pros

- Keeps validation near object creation
- Prevents invalid objects
- Useful in real projects

### ❌ Cons

- More logic inside builder
- Must keep validation simple and clear

</details>

---

# 🧱 9. Full Recommended Code

```java
class User {
    private final String name;
    private final String email;
    private final int age;
    private final String city;
    private final boolean active;

    private User(Builder builder) {
        this.name = builder.name;
        this.email = builder.email;
        this.age = builder.age;
        this.city = builder.city;
        this.active = builder.active;
    }

    public static class Builder {
        private final String name;
        private final String email;
        private int age;
        private String city;
        private boolean active;

        public Builder(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder active(boolean active) {
            this.active = active;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        User user = new User.Builder("Rahul", "rahul@email.com")
                .age(25)
                .city("Delhi")
                .active(true)
                .build();
    }
}
```

---

# 📊 10. Constructor vs Setter vs Builder

| Topic | Constructor | Setter | Builder |
|---|---|---|---|
| Readability | Low with many fields | Good | Excellent |
| Optional fields | Hard | Easy | Easy |
| Immutability | Possible | Usually no | Excellent |
| Object consistency | Good | Can be incomplete | Good |
| Best use | Few required fields | Mutable objects | Many optional fields |

---

# 🧱 11. Notion Column Layout Idea

Use this as a 2-column revision section after importing into Notion:

| 🧠 Remember | ⚠️ Avoid |
|---|---|
| Builder creates objects step by step | Huge constructors |
| Great for optional fields | Builder for tiny classes |
| `build()` returns final object | Forgetting required fields |
| Supports immutable objects | Putting unrelated logic in builder |

---

# 🔁 12. Synced Block Content

> 📌 **Copy this into a Notion Synced Block:**  
> Builder Pattern creates complex objects step by step.  
> It improves readability when a class has many fields, especially optional fields.

---

# 🧩 13. Practice Template

## Builder Pattern Revision Template

- **Definition:** Creates complex objects step by step.
- **Main problem solved:** Too many constructor parameters.
- **Best use case:** Required fields + optional fields.
- **Recommended Java style:** Static inner Builder class.
- **Main benefit:** Readable and flexible object creation.

### ☑️ Checklist

- [ ] I can define Builder simply
- [ ] I can explain telescoping constructors
- [ ] I can write a static inner Builder
- [ ] I know why Builder helps optional fields
- [ ] I can compare constructor, setter, and builder
- [ ] I can explain how Builder supports immutability

---

# 🎤 14. Common Interview Questions

| Question | Short Answer |
|---|---|
| What is Builder Pattern? | A creational pattern used to create complex objects step by step. |
| Why do we use Builder? | To avoid confusing constructors with many parameters. |
| What is telescoping constructor problem? | Too many overloaded constructors for different field combinations. |
| When should Builder be used? | When a class has many fields, especially optional fields. |
| How does Builder improve readability? | Method names show what each value means. |
| Can Builder create immutable objects? | Yes, by using `final` fields and no setters. |
| What does `build()` do? | It creates and returns the final object. |
| Is Builder useful for small classes? | Usually no, constructor is enough. |
| What is the common Java Builder style? | Static inner Builder class. |
| Builder vs Setter? | Builder can create complete immutable objects; setters usually create mutable objects. |

---

# 📝 15. Revision Cheat Sheet

## 🧠 Core Formula

```java
ClassName obj = new ClassName.Builder(requiredValue)
        .optionalValue(value)
        .build();
```

## ⭐ Memory Hook

> **Constructor = all at once**  
> **Builder = step by step**

## ✅ Key Takeaways

- Builder is a **creational design pattern**
- It creates complex objects step by step
- It avoids long confusing constructors
- It is best when many fields are optional
- It improves readability
- It can support immutable objects
- Static inner Builder is common in Java interviews

## ⚠️ Common Beginner Mistakes

- Using Builder for very small classes
- Forgetting the final `build()` method
- Making required fields optional by accident
- Putting business logic inside the builder
- Confusing Builder with Factory Method

---

> 🎯 **Final Interview Line:**  
> "Builder Pattern is used when an object has many fields, especially optional ones. It creates the object step by step, improves readability, avoids telescoping constructors, and can help build immutable objects."
