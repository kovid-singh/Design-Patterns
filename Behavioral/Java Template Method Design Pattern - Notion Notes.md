# 🧱 Java Template Method Design Pattern

> 💡 **Goal:** Master Template Method Pattern from scratch for Java interviews using simple explanations, visual notes, and easy Java code.

---

---

# 📌 1. What Is Template Method?

> 🧩 **Template Method is a behavioral design pattern that defines the skeleton of an algorithm in a base class and lets subclasses change specific steps without changing the overall structure.**

In simple words:

> **Same overall process -> some steps fixed -> some steps customizable**

Example idea:

```java
Beverage beverage = new Tea();
beverage.prepareRecipe();
```

The base class controls the order of steps, while subclasses fill in the custom parts.

---

# 🤔 2. Why Do We Need Template Method?

Template Method is useful when many classes follow the same overall process, but some individual steps differ.

Without Template Method:

```java
class Tea {
    void prepare() {
        boilWater();
        steepTea();
        pourInCup();
        addLemon();
    }
}

class Coffee {
    void prepare() {
        boilWater();
        brewCoffee();
        pourInCup();
        addSugarAndMilk();
    }
}
```

The structure is repeated in multiple classes.

With Template Method:

```java
Beverage beverage = new Coffee();
beverage.prepareRecipe();
```

> 🚨 **Problem Template Method solves:**  
> It avoids repeated algorithm structure while allowing subclasses to customize certain steps.

---

# 🥤 3. Real-Life Analogy

> 🥤 **Making Drinks Analogy**  
> Tea and coffee follow a similar process:
>
> - boil water
> - prepare the main ingredient
> - pour into cup
> - add extras
>
> The overall steps are the same, but some details change.

In code:

- Common recipe = template method
- Tea and coffee = subclasses
- Different brewing step = custom implementation

> 💡 **Simple meaning:**  
> Template Method gives a fixed recipe with customizable steps.

---

# 🎯 4. When To Use Template Method

| ✅ Use Template Method When | Example |
|---|---|
| Many classes follow the same process | Beverage preparation |
| Only a few steps differ | Report generation |
| You want to avoid repeated algorithm structure | Data parsing |
| You want controlled extension points | Framework lifecycle methods |
| Order of steps must stay fixed | File processing |

---

# 🚫 5. When NOT To Use Template Method

| ❌ Avoid Template Method When | Why |
|---|---|
| There is no common algorithm structure | Base template adds no value |
| Behavior should change by composition, not inheritance | Strategy may fit better |
| Steps vary too much between classes | Template becomes forced |
| Inheritance hierarchy becomes too rigid | Harder to maintain |
| You need runtime switching of behavior | Strategy is better |

> ⚠️ **Beginner Warning:**  
> Template Method uses inheritance, so it can become rigid if overused.

---

# ✅ 6. Advantages and Disadvantages

| ✅ Advantages | ❌ Disadvantages |
|---|---|
| Reuses common algorithm structure | Depends on inheritance |
| Reduces duplicate code | Less flexible than composition |
| Controls order of steps | Changes in base class affect subclasses |
| Makes extension points clear | Deep inheritance can become harder to manage |
| Good for framework-style design | Runtime behavior changes are harder |

---

# 🧠 7. Core Roles In Template Method

| Role | Meaning |
|---|---|
| `AbstractClass` | Base class defining the template method |
| `Template Method` | Method containing the fixed algorithm structure |
| `Primitive Operations` | Steps that subclasses implement |
| `ConcreteClass` | Subclass providing custom step behavior |

---

# 💻 8. Java Implementations

> 🧠 **Important Java interview point:**  
> Template Method is based on **inheritance**, unlike Strategy which uses composition.

---

<details open>
<summary>🔽 1. Basic Template Method Example (Beverage)</summary>

## Beverage Example

This is the classic example for Template Method.

```java
abstract class Beverage {
    public final void prepareRecipe() {
        boilWater();
        brew();
        pourInCup();
        addCondiments();
    }

    private void boilWater() {
        System.out.println("Boiling water");
    }

    private void pourInCup() {
        System.out.println("Pouring into cup");
    }

    protected abstract void brew();
    protected abstract void addCondiments();
}

class Tea extends Beverage {
    @Override
    protected void brew() {
        System.out.println("Steeping the tea");
    }

    @Override
    protected void addCondiments() {
        System.out.println("Adding lemon");
    }
}

class Coffee extends Beverage {
    @Override
    protected void brew() {
        System.out.println("Brewing coffee");
    }

    @Override
    protected void addCondiments() {
        System.out.println("Adding sugar and milk");
    }
}
```

Client code:

```java
public class Main {
    public static void main(String[] args) {
        Beverage tea = new Tea();
        tea.prepareRecipe();

        Beverage coffee = new Coffee();
        coffee.prepareRecipe();
    }
}
```

### ✅ Why this is useful

- Common flow is written once
- Subclasses customize only the different parts
- Order of steps stays controlled

</details>

---

<details>
<summary>🔽 2. Template Method With Hook</summary>

## Hook Method Example

A hook is an optional method that subclasses may override.

```java
abstract class DataProcessor {
    public final void process() {
        readData();
        processData();
        if (shouldSave()) {
            saveData();
        }
    }

    protected void readData() {
        System.out.println("Reading data");
    }

    protected abstract void processData();

    protected void saveData() {
        System.out.println("Saving data");
    }

    protected boolean shouldSave() {
        return true;
    }
}

class CsvProcessor extends DataProcessor {
    @Override
    protected void processData() {
        System.out.println("Processing CSV data");
    }
}

class TestProcessor extends DataProcessor {
    @Override
    protected void processData() {
        System.out.println("Processing test data");
    }

    @Override
    protected boolean shouldSave() {
        return false;
    }
}
```

Usage:

```java
public class Demo {
    public static void main(String[] args) {
        DataProcessor csv = new CsvProcessor();
        csv.process();

        DataProcessor test = new TestProcessor();
        test.process();
    }
}
```

### 📌 Key idea

Hooks provide optional customization without forcing every subclass to override everything.

</details>

---

<details>
<summary>🔽 3. Report Generation Example</summary>

## Report Generation Example

Template Method is common when multiple report types follow the same overall flow.

```java
abstract class ReportGenerator {
    public final void generateReport() {
        fetchData();
        formatData();
        exportReport();
    }

    protected void fetchData() {
        System.out.println("Fetching data");
    }

    protected abstract void formatData();

    protected void exportReport() {
        System.out.println("Exporting report");
    }
}

class PdfReport extends ReportGenerator {
    @Override
    protected void formatData() {
        System.out.println("Formatting data as PDF");
    }
}

class ExcelReport extends ReportGenerator {
    @Override
    protected void formatData() {
        System.out.println("Formatting data as Excel");
    }
}
```

Usage:

```java
public class Main {
    public static void main(String[] args) {
        ReportGenerator pdf = new PdfReport();
        pdf.generateReport();

        ReportGenerator excel = new ExcelReport();
        excel.generateReport();
    }
}
```

### ✅ Why mention this in interviews

- Shows a practical business use case
- Easy to connect with framework and reporting systems

</details>

---

# 📊 9. Inheritance vs Repeated Code

| Topic | Without Template Method | With Template Method |
|---|---|---|
| Common algorithm structure | Repeated | Reused in base class |
| Step customization | Mixed into each class | Isolated in subclasses |
| Control of order | Harder to enforce | Fixed in template method |
| Maintainability | Lower | Higher |
| Code duplication | More | Less |

---

# 🔄 10. Flow Of Template Method

```text
Client -> AbstractClass.templateMethod() -> fixed steps + subclass steps
```

Step by step:

1. Client calls the template method
2. Base class runs fixed steps
3. Base class calls abstract or hook methods
4. Subclass provides custom behavior for those steps

> 📌 **Memory hook:**  
> Same recipe, custom ingredients.

---

# 🧱 11. Template Method vs Similar Patterns

| Pattern | Main Purpose |
|---|---|
| Template Method | Fixes algorithm structure but allows some steps to vary |
| Strategy | Switches full algorithms using composition |
| Factory Method | Creates objects through a method |
| Command | Wraps requests as objects |
| Decorator | Adds behavior by wrapping objects |

> ⚠️ **Most common confusion:**  
> Template Method uses **inheritance**.  
> Strategy uses **composition**.

---

# 🧱 12. Notion Column Layout Idea

Use this as a 2-column revision section after importing into Notion:

| 🧠 Remember | ⚠️ Avoid |
|---|---|
| Template Method defines fixed algorithm structure | Confusing it with Strategy |
| Subclasses change only selected steps | Forcing unrelated classes into one hierarchy |
| Great for common workflows | Using inheritance when runtime flexibility is needed |
| Hooks give optional customization | Making base class too rigid |

---

# 🔁 13. Synced Block Content

> 📌 **Copy this into a Notion Synced Block:**  
> Template Method Pattern defines the skeleton of an algorithm in a base class and lets subclasses customize certain steps.  
> It is useful when many classes follow the same overall process.

---

# 🧩 14. Practice Template

## Template Method Pattern Revision Template

- **Definition:** Template Method defines a fixed algorithm structure and lets subclasses customize specific steps.
- **Main problem solved:** Repeated workflow structure across multiple classes.
- **Real example:** Tea vs coffee, report generation, data processing.
- **Main rule:** Base class controls the algorithm order.
- **Main benefit:** Reuse with controlled customization.

### ☑️ Checklist

- [ ] I can define Template Method simply
- [ ] I can explain template method and primitive operations
- [ ] I can write a beverage example
- [ ] I know what a hook method is
- [ ] I can compare Template Method with Strategy
- [ ] I can give one practical business example

---

# 🎤 15. Common Interview Questions

| Question | Short Answer |
|---|---|
| What is Template Method Pattern? | A behavioral pattern that defines the skeleton of an algorithm in a base class and lets subclasses customize steps. |
| Why do we use Template Method? | To reuse common workflow structure and avoid duplicate code. |
| What problem does Template Method solve? | Repetition of similar algorithm flow across multiple classes. |
| Does Template Method use inheritance or composition? | Inheritance. |
| What is a hook method? | An optional method that subclasses may override. |
| Template Method vs Strategy? | Template Method uses inheritance; Strategy uses composition. |
| Why is the template method often `final`? | To prevent subclasses from changing the algorithm order. |
| Give one real Java-like use case. | Report generation or beverage preparation workflow. |
| When should you avoid Template Method? | When classes do not share a stable common process or when runtime flexibility is needed. |
| What is the benefit of fixed algorithm structure? | It ensures consistency while still allowing controlled customization. |

---

# 📝 16. Revision Cheat Sheet

## 🧠 Core Formula

```java
abstract class Base {
    final void templateMethod() {
        step1();
        step2();
        customStep();
    }

    abstract void customStep();
}
```

## ⭐ Memory Hook

> **Template Method = fixed recipe, customizable steps**

## ✅ Key Takeaways

- Template Method is a **behavioral design pattern**
- It defines the **skeleton of an algorithm**
- It uses **inheritance**
- Common steps stay in the base class
- Variable steps are implemented by subclasses
- Good for **workflows, processing pipelines, and framework hooks**

## ⚠️ Common Beginner Mistakes

- Confusing Template Method with Strategy
- Forgetting that inheritance can make design rigid
- Allowing subclasses to break algorithm order
- Repeating logic in subclasses instead of base class
- Using Template Method when classes do not share a real common process

---

> 🎯 **Final Interview Line:**  
> "Template Method Pattern defines the overall structure of an algorithm in a base class and lets subclasses customize specific steps. It is useful when many classes share the same workflow but differ in a few parts."
