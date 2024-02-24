# Portfolio Server
## Hey, Future Me! 
### Here's the deployment instructions:
By default, the key pair for signing JWTs are stored in `resources/keys` and are not pushed to the repository. You have to tell Docker where the keypair is and modify the source parameter of the copy command in the Dockerfile. 
```Dockerfile
COPY src/main/resources/keys/private.der ./src/main/resources/private.der
COPY src/main/resources/keys/public.der ./src/main/resources/public.der
```
Have a look at this if you are having issues generating keys:
```shell
# Generate RSA private key
openssl genpkey -algorithm RSA -outform PEM -out private_key.pem 

# Create a file with DER encoded, PKCS8 formatted private key
openssl pkcs8 -nocrypt -topk8 -inform PEM -in private_key.pem -outform DER -out private.der

# Create file containing DER encoded public key
openssl rsa -in private_key.pem -pubout -outform DER -out public.der
```
If you want to run on your local machine, generate the keys in the keys folder. 
If you want to use an existing key pair, check out the `keys.location.*` configuration properties in `application.yaml`

<hr/>

## Why Kotlin?
### Syntactic sugar, baby!
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