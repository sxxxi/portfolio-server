

### Why Kotlin?
Kotlin is expression-oriented, and has syntax that is short and concise enabling for rapid development, and
easier reading. For example:
```java
class TestObject {}
```
```java
class NullChecker {
   private TestObject testObject = null;
    
   public String objectValue() {
       if (testObject == null) {
           return "testObject is null";
       } else {
           return "testObject has a value";
       }
   }
}
```
```kotlin
class NullChecker {
    var testObject: TestObject? = null
    
    fun objectValue(): String {
        return testObject?.let {
            "testObject has a value"
        } ?: "testObject is null"
    }
}
```