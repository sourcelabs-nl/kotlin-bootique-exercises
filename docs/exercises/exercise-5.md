## Exercise 5: Using Kotlin in your test code

Last but not least, convert the test code from Java to Kotlin and add a Unit test. As with any type
of application, we really, really, really encourage testing. It's just a good idea.

### Write a simple unit test

Let's start out by finally adding a simple unit test. Normally we would strive towards maximum (branch)
coverage but for now we'll just settle for being able to test one of our components. Let's start by
writing a test for the BootiqueController class.

**Exercise** Create a test class for the BootiqueController

Most of you working in intellij will be familiar with this and probably know the shortcut
to trigger the 'create test' intention. You can also create it manually in the source/test/java
folder of course. Name the file BootiqueControllerTest, and put it in the same package as the
class we are going to test.

We are going to be using a mocking framework to mock out our dependencies. Spring test 
conveniently bundles Mockito so let's use that. First thing to do now, is to define the 
MockitoRunner as the testrunner for your unit test. Add it now.

In java:

```java
@RunWith(MockitoJUnitRunner.class)
public class BootiqueControllerTest {}
```

<details>
<summary>Snippet</summary>

In Kotlin we can refer to classes with the double colon notation: `MockitoJUnitRunner::class`

```kotlin
@RunWith(MockitoJUnitRunner::class)
class BootiqueControllerTest
```
</details>
<br>

**Exercise** Define the instance we are testing and the mocks Mockito should inject

We are now going to define the unit we are testing and the mocks required by this unit
to function. In Java, we'd do something like this:

```java
@InjectMocks
private BootiqueController bootiqueController;

@Mock
private ProductRepository mockProductRepository;

@Mock
private ProductRepository mockBasketRepository;
```

Without converting (because that is almost like cheating ;) , define the same thing in Kotlin.

<details>
<summary>Snippet</summary>

This is something interesting. Kotlin has a typesystem that by default does not allow null 
values. This means we need to work around the fact that we cannot initialize the tested class
and mocks at compile time -- we do this at runtime.

Kotlin defines a simple way to do this, by leveraging the lateinit keyword. This keyword
can also be used for property/field injection at runtime (although you should probably avoid
doing that anyway).

When using lateinit you logically have to specify the target type for the variable, as this
can not be inferred.

```kotlin
@InjectMocks
private lateinit var bootiqueController: BootiqueController

@Mock
private lateinit var mockBasketRepository: BasketRepository

@Mock
private lateinit var mockProductRepository: ProductRepository
```
</details>
<br>

**Exercise** Write a simple test for the `getBasket()` operation.

To do this, we'd have to use the basketRepository mock, and instruct it to behave in a certain
way. This is the test you could possibly write in java:

```java
@Test
public void testRetrieveBasket() {
    final String basketId = "BasketId";
    final Basket basket = new Basket();
    
    when(mockBasketRepository.getBasketById(basketId)).thenReturn(basket);
    
    assertThat(bootiqueController.getBasket(basketId)).isEqualTo(basket);
    
    verify(mockBasketRepository).getBasketById(basketId);
}
```

Now create the Kotlin equivalent.

<details>
<summary>Snippet</summary>

The code is not that different from the Java, but there's catch! In Kotlin `when` is a keyword!
Luckily, this was anticipated so in Kotlin we can still use function calls with backticks 
around them. Besides this, it's all the same.

Another interesting possibility is that Kotlin allows you to use whitespace in function names,
as long as you use backticks around them. This allows you to write more expressive test method
names.

```kotlin
@Test
fun `test retrieving basket functionality`() {
    val basketId = "BasketId"
    val basket = Basket()
    
    `when`(mockBasketRepository.getBasketById(basketId)).thenReturn(basket)
    
    assertThat(bootiqueController.getBasket(basketId)).isEqualTo(basket)
    
    verify(mockBasketRepository).getBasketById(basketId)
}
```
</details>

**Pro tip if you are stuck: Write the test in java and convert/copy it into a kotlin file. The conversion
will be automatic and can help you figuring out how to write some of the code.** 

**Exercise** Remove the need for backticks around the when

If the backticks around `when` give you a headache too you can write a helper function that encapsulates 
this. The definition for Mockito.when is the following:

```java
public static <T> OngoingStubbing<T> when(T methodCall) {
    return MOCKITO_CORE.when(methodCall);
}
``` 

It is a convenient static function, so we can write a Kotlin function that wraps this and substitutes the
definition of `when` with `whenever`.

<details>
<summary>Snippet</summary>

Here's the Kotlin code for that, you could add it to the test sources for convenient usage.

```kotlin
fun <T> whenever(methodCall: T): OngoingStubbing<T> {
    return Mockito.`when`(methodCall) // Delegate to escaped when
}

// Usage
whenever(mockBasketRepository.getBasketById(basketId)).thenReturn(basket)
```
</details>

You can also add a nifty library to your codebase named [Mockito-Kotlin-Library](https://github.com/nhaarman/mockito-kotlin)
which enables you to use mockito when as whenever but also has some very nice functions added to make
testing Kotlin with Mockito a breeze. It adds some simple syntactic sugar where it makes sense.

### Convert BootiqueApplicationTests.java to Kotlin

**Exercise**: Convert BootiqueApplicationTests.java to Kotlin using IntelliJ (menu > Code > Convert Java File to Kotlin File).

This application stems from [start.spring.io](http://start.spring.io) and because of that it features a
test setup already, for application tests. We are converting our codebase to Kotlin though, so that means
we can't really leave this one in its Java form. 

The first step would be to convert the test to Kotlin code, so do so now.

**Exercise**: Modify the test setup for calling an endpoint in the running bootique service

We are going to test the app by calling an endpoint, so we'll be creating a bit of an
application test. We are first going to tell Spring Boot to start a server (on a random port)
and will wire in a TestRestTemplate to call the service.

First, we'll need to configure the web environment to test against. Modify the `@SpringBootTest`
annotation like below.

```java
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
```

Secondly, wire in an instance of the `TestRestTemplate` that will be provided to you by spring
when activating the web environment. It will be provided with host and port preconfigured.

```java
@Autowired
private TestRestTemplate testRestTemplate;
```

Adapt the logic above to Kotlin for use in the `BootiqueApplicationTests`.

<details>
<summary>Snippet</summary>

Here's the Kotlin implementation for this:

```kotlin
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class BootiqueApplicationTest {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate 
```
</details>

Run the setup, the context should load, injection of the `TestRestTemplate` should succeed.

**Exercise**: Write the test actual test

Let's define the test like so. Implement the body of this function, by using the `TestRestTemplate` 
to call the service.

```kotlin
@Test
fun `test get products endpoint`() {
}
```

The `/products` endpoint returns a list of products. In order to employ automatic conversion
to List<Product> we can use a class called `ParameterizedTypeReference` which will allowed a 
typed return value for the template. Consider the following call:

```kotlin
restTemplate.exchange("/products", HttpMethod.GET, null, object: ParameterizedTypeReference<List<Product>>() {})
```

This will return a `ResponseEntity<List<Product>>` because the type is enforced thanks to the
ParametrizedTypeReference. It is a bit verbose though, and requires the use of an inner class.

We could try using a convenience method this class provides, but that won't work like this:

But -- what if we could call an endpoint like this:

```kotlin
val products = restTemplate.get<List<Product>>("/products")
```

That would be quite nice. As a final exercise, let's leverage three interesting features Kotlin
has to offer: Extension functions and inlining + reified generics.

Extension functions allow us to 'add' functionality to an already existing class. This is no
bytecode magic, but merely some syntactic sugar that creates a function that takes the instance
it is called on as an implicit parameter and exposes it as `this`.

Secondly, we can employ generics, more specifically reified generics, to extract the type from
a generic parameter **at runtime**. Kotlin can inline function code at the call site, eliminating
the need for dynamic calls but also adding some extra flexibility: by inlining the code at the
call site, Kotlin is able to escape from the type erasure that haunts the JVM.

Try to implement an extension function for `TestRestTemplate`, named `get(uri: String)` which
delegates to the `exchange()` method.

For reference, the syntax below shows an extension function on the `TestRestTemplate` type, 
in this case with a void return type. It also defines generics. Modify this call so you can 
call it like listed below.

```kotlin
val products = restTemplate.get<List<Product>>("/products") // Usage

fun <T> TestRestTemplate.get(url: String): T {
    return ...
}
``` 

<details>
<summary>Snippet</summary>

There are two main modifications required to the method to use reified generics. One is defining
the method to be inlined. You cannot reify generics without inlining, due to JVM type erasure.
We need to apply the `inline` keyword to the function.

Secondly, we need to add the `reified` keyword in the generic declaration, to define our 
intent to reify the generic type.

Having done this, we can now refer to the generic type as usual, but we can also extract the
type at runtime, which means we can actually use `T::class.java` to get the runtime type of 
the class!

Putting it all together, we can now conveniently call get with just the url path, and the
specified generic type, like this: `restTemplate.get<List<Product>>("/products")`. Because
of the reified generics we can use `ParameterizedTypeReference.forType<T>(T::class.java)` 
eliminating the need for an anonymous inner class.

```kotlin
inline fun <reified T> TestRestTemplate.get(url: String): T = this.exchange(
        url, HttpMethod.GET, null, ParameterizedTypeReference.forType<T>(T::class.java)
).body
```
</details> 

If you were able to succesfully make the changes to the method above, you can then implement
the test like so:

```kotlin
@Test
fun `test get products endpoint`() {
    val products = restTemplate.get<List<Product>>("/products")
    assertThat(products.size).isEqualTo(4)
}
```

Running it should succeed. This should give you some insight in how Kotlin integrates well with
the existing test setup you may already have, how to deal with reserved names and how to write
and extension function with reified generics to allow convenient and expressive usage of an 
injected rest template.
