(ns learn-clojure.basics)

;;
;; ## Clojure basics
;;
;; I will try to introduce concepts gradually without assuming prior
;; knowledge of Clojure (or any other LISP dialect).  However I will
;; assume that you are already an experienced developer in any other
;; popular language such as Java, C/C++, Python or Javascript.
;; General programming concepts such as functions, parameters,
;; recursion, objects and common data-structures such as: linked
;; lists, maps (or dictionaries), vectors and sets will be assumed to
;; be already known.
;;
;;
;;
;; ### The function call.
;;
;; The first concept I will introduce is how to make a function call.
;; We will see more about functions later, but for the moment
;; I want to make sure that you will understand the next few examples.
;; Let's start to make some comparisons with method or function
;; calls in a few different languages
;;
;;     // java and C++
;;     myObject.myFunction(arg1, arg2, arg3);
;;
;;     // C
;;     myFunction(myStruct, arg1, arg2, arg3);
;;
;;     ;; Clojure
;;     (my-function myObject arg1 arg2 arg3)
;;
;;
;; As you can see in Clojure the brackets surround the function and
;; all its arguments.  In object oriented languages such as Java and
;; C++ the object comes before the method name or function name. In C
;; and Clojure the function comes first, then followed by the target
;; object. Let's see a concrete example, _for the sake of the example
;; I will omit the required package imports._
;;
;;
;;     // java
;;     "Hello World!".toLowerCase();
;;
;;     // C - single char
;;     tolower(*c);
;;     // C - Whole string
;;     for ( ; *c; ++c) *c = tolower(*c);
;;
;;     ;; Clojure
;;     (lower-case "Hello World!")
;;
;;
;; _NOTE: In the standard C library there is only a function to turn a
;; single character into its lowercase form, that's why there is a
;; loop._
;;
;; However in the `tolower(*c)` we can see the function comes first
;; followed by its arguments surrounded by bracket.  In Clojure, the
;; expression (called `s-expr`) starts with an open bracket,
;; followed by a function followed by a list of arguments.
;;
;; The following code is designed to run in the Clojure REPL, the
;; conventions I will follow throughout the text is to display the
;; result of the expression evaluation prefixed with this evaluation
;; marker `;;=>`. So every time you'll see a Clojure expression
;; followed by `;;=>` and followed by another value it means that the
;; result of the evaluation of the prior expression is what follow the
;; marker. For example the evaluation of the expression `(+ 1 1)` with
;; its result will be noted as follow:
;;
;;     (+ 1 1)
;;     ;;=> 2
;;
;; ### Booleans
;;
;; In Clojure we have boolean values like in many other languages.  No
;; surprise here we have two values `true` and `false` which just
;; evaluate to themselves.  Now we can use the function `type` to see
;; what is the concrete type of these values in the host platform, and
;; if we check the type of these values we'll find that they are just
;; simple Java `java.lang.Boolean` objects.
;;

true
;;=> true

false
;;=> false

(type true)
;;=> java.lang.Boolean

;;
;; Now boolean values are often associated to **logic programming** and
;; the concept of **"truthiness"**. In strongly typed languages such as Java
;; you can only use boolean in conditional operation.
;; Some other languages such C/C++ have a more lose definition **"truthiness"**.
;; __In Clojure everything is considered **true** with the exception of `false`
;; and `nil`.__
;;
;; For example we can use the following form `(if condition truthy falsey)`
;; which evaluates the given `condition` and if the condition has a logical
;; value of true then it will evaluate `truthy` form otherwise it evaluates
;; the `falsey`.
;;

(if true "it's true" "it's false")
;;=> "it's true"

(if false "it's true" "it's false")
;;=> "it's false"

(if nil "it's true" "it's false")
;;=> "it's false"

(if "HELLO" "it's true" "it's false")
;;=> "it's true"

(if 1 "it's true" "it's false")
;;=> "it's true"

;;
;; ### Numbers
;;
;; Clojure has a quite unique support for numerical values.
;; As you would expect every number just evaluates to itself.
;;
;; #### Integers
;;
;; They are mapped to `java.lang.Long`, but since they can
;; be indefinitely large they can be promoted to `clojure.lang.BigInt`
;; once they go beyond the `java.lang.Long#MAX_VALUE`.

1 ;;=> 1
-4 ;;=> -4

9223372036854775807   ; java.lang.Long#MAX_VALUE
;;=> 9223372036854775807

(type 1)
;;=> java.lang.Long

(type 9223372036854775807)
;;=> java.lang.Long

29384756298374652983746528376529837456
;;=> 29384756298374652983746528376529837456N

(type 29384756298374652983746528376529837456)
;;=> clojure.lang.BigInt

(type 1N)
;;=> clojure.lang.BigInt

;;
;; You can also define integers literals in other basis
;; such as octal, hexadecimals and binary.
;;

127 ;;=> 127              ; decimal
0x7F ;;=> 127             ; hexadecimal
0177 ;;=> 127             ; octal
32r3V ;;=> 127            ; base 32
2r01111111 ;;=> 127       ; binary
36r3J ;;=> 127            ; base 36

36rClojure ;;=> 27432414842
2r0111001101010001001001 ;;=> 1889353

;;
;; In Clojure there are no operators, in fact `+`, `-`, `*` and `/`
;; are normal functions.

(+ 1 2 3 4 5)
;;=> 15

;;
;; You can access static fields by adding providing the fully
;; qualified class name followed by a slash (`/`) and the field name,
;; for example: `java.lang.Long/MAX_VALUE`.
;;

java.lang.Long/MAX_VALUE
;;=> 9223372036854775807

(- java.lang.Long/MAX_VALUE 1)
;;=> 9223372036854775806

(+ 1 java.lang.Long/MAX_VALUE)
;;=> ArithmeticException integer overflow


;;
;; Clojure has a number of functions which will automatically
;; auto-promote the number to be bigger type in case it doesn't
;; fit in the 64bit Java Long object. These functions are:
;; `+'`, `-'` and `*'`
;;

(+' 1 java.lang.Long/MAX_VALUE)
;;=> 9223372036854775808N

(*' java.lang.Long/MAX_VALUE java.lang.Long/MAX_VALUE)
;;=> 85070591730234615847396907784232501249N


;;
;; #### Decimals
;;
;; Clojure supports floating point decimals and exact decimals.
;; Floating point decimals are mapped to `java.lang.Double` and they
;; evaluate to themselves. While exact decimals are mapped to
;; `java.math.BigDecimal` and they also evaluate to themselves.  Use
;; the latter when you require exact decimals but be careful to
;; numbers which can't be represented with exact decimals like: 1
;; divided by 3 (0.3333333...) as the the decimal part continue
;; forever.
;;

3.2
;;=> 3.2

(type 3.2)
;;=> java.lang.Double

3.2M
;;=> 3.2M

(type 3.2M)
;;=> java.math.BigDecimal

(+ 0.3 0.3 0.3 0.1) ;; floating point
;;=> 0.9999999999999999

(+ 0.3M 0.3M 0.3M 0.1M) ;; big-decimal
;;=> 1.0M

(/ 1.0M 3.0M)
;;=> ArithmeticException Non-terminating decimal expansion; no exact representable decimal result.

(with-precision 10 (/ 1.0M 3.0M))
;;=> 0.3333333333M

;;
;; #### Rationals
;;
;; Number like 1 divided by 3 are called rational numbers,
;; and Clojure supports them. You can mix then in your
;; calculation and as long as you don't put floating point
;; values it will retain the precision.
;;

(/ 1 3)
;;=> 1/3

(type 1/3)
;;=> clojure.lang.Ratio

(+ 1/3 1/3 1/3)
;;=> 1N

(/ 21 6)
;;=> 7/2

(+ 1/3 1/3 1/3 1)
;;=> 2N

(+ 1/3 1/3 0.333)
;;=> 0.9996666666666667


;;
;;
;; ### Characters
;;
;; So far we have seen the rich support for numerical values in Clojure.
;; Clojure does support characters and strings literals as well.
;; Characters map to `java.lang.Character`, support Unicode characters
;; and as all value-types they evaluate to themselves.
;;

\a       ; this is the character 'a'
\A       ; this is the character 'A'
\\       ; this is the character '\'
\u0041   ; this is unicode for  'A'
\tab     ; this is the tab character
\newline ; this is the newline character
\space   ; this is the space character

\a ;;=> \a

(type \a)
;;=> java.lang.Character

;;
;;
;; ### Strings
;;
;; Strings literals have no surprise. They map to `java.lang.String`,
;; they are multi-line, like in Java they are immutable and they evaluate
;; to themselves.
;;

"This is a string"
;;=> "This is a string"

(type "This is a string")
;;=> java.lang.String

"Strings in Clojure
 can be multi lines
 as well!!"
;;=> "Strings in Clojure\n can be multi lines\n as well!!"

;;
;; Via the Java interop. infrastructure you can call
;; all `java.lang.String` methods directly
;;

(.toUpperCase "This is a String")
;;=> "THIS IS A STRING"

;;
;; You can use the function `str` to concatenate strings or to convert
;; numbers into strings (via `Object#toString()` method).
;;

(str "This" " is " "a" " concatenation.")
;;=> "This is a concatenation."

(str "Number of lines: " 123)
;;=> "Number of lines: 123"


;;
;; ### Keywords
;;
;; Keywords are labels for things in our programs, they evaluate to themselves
;; and can be used to give name to things similarly to Java's enumerations.
;; They mostly used as key in maps (we will see this later), and the Clojure
;; runtime maintains them in a internal pool (similarly to interned strings in Java.)
;; which guarantee that only one copy of a particular keyword will ever exist in a
;; program. For this reason they provide very fast equality test.
;; Equality test in Clojure is done via the function `=` with the same semantic
;; as the Java's `.equals()` method, while the identity equality is done via
;; the function `identical?` which in turn implements the Java's `==` operator.
;; You can use the function `keyword` to create a keyword out of a string.
;;


:words
;;=> :words

(type :this-is-a-keyword)
;;=> clojure.lang.Keyword

(keyword "blue")
;;=> :blue

(= :blue :blue)
;;=> true

(= (str "bl" "ue") (str "bl" "ue"))
;;=> true

(identical? :blue :blue)
;;=> true

(identical? (str "bl" "ue") (str "bl" "ue"))
;;=> false

(identical? (keyword (str "bl" "ue")) (keyword (str "bl" "ue")))
;;=> true

;;
;; ### Collections
;;
;; In Java the only collection literals available is the array.
;; Clojure like most of modern languages offers a variety of
;; collection literals which makes the language more expressive,
;; out-of-the-box are supported the following collections literals:
;; single linked lists, vectors, maps (or dictionaries) and sets.
;; However Clojure supports a larger number of data structures
;; which are built with functions such as: sorted maps, sorted sets,
;; array maps, hash maps, hash sets and many more are available
;; in community maintained library such as graphs, ring buffers
;; and AVL trees. **All Clojure collections can contain
;; a mixture of values**.
;;
;;
;; #### Lists
;;
;; Clojure has single-linked lists built-in and like all other
;; Clojure collections are immutable.
;; Lists guarantee `O(1)` insertion on the head, `O(n)` traversal
;; and element search.
;;

; to create a list you can use the function `list`
(list 1 2 3 4 5)
;;=> (1 2 3 4 5)

; to "add" an element on the front of the list you can
; use the `cons` function.
(cons 0 (list 1 2 3 4 5))
;;=> (0 1 2 3 4 5)

;; As the output suggest the lists literals in Clojure are expressed
;; with a sequence of values surrounded by brackets,
;; which is the same of the function call. That is the reason why
;; the following line throws an error.

(1 2 3 4 5)
;;=> ClassCastException java.lang.Long cannot be cast to clojure.lang.IFn

;; To be able to express a list of values as a literal we have to used
;; the `quote` form which it will preserve the list without initiate
;; the function call.

(quote (1 2 3 4 5))
;;=> (1 2 3 4 5)

;;
;; As syntax sugar we can use the single quote sign `'` instead of the
;; longer `(quote ,,,)` form.
;;

'(1 2 3 4 5)
;;=> (1 2 3 4 5)

'(1 "hi" :test 4/5 \c)
;;=> (1 "hi" :test 4/5 \c)

;;
;; you can get the head of the list with the function `first`
;; and use `rest` or `next` to get the tail. `count` returns
;; the number of elements in it. `nth` returns the nth element
;; of the list, while `last` returns last item in the list.
;;

(first '(1 2 3 4 5))
;;=> 1

(rest '(1 2 3 4 5))
;;=> (2 3 4 5)

(next '(1 2 3 4 5))
;;=> (2 3 4 5)

(rest '(1))
;;=> ()

(next '(1))
;;=> nil

(count '(5))
;;=> 1

(count '(1 2 3 4 5))
;;=> 5

(nth '(1 2 3 4 5) 0)
;;=> 1

(nth '(1 2 3 4 5) 1)
;;=> 2

(nth '(1 2 3 4 5) 10)
;;=> IndexOutOfBoundsException

(nth '(1 2 3 4 5) 10 :not-found)
;;=> :not-found

(last '(1 2 3 4 5))
;;=> 5

(last '(1))
;;=> 1

(last '())
;;=> nil

;;
;; #### Vectors
;;
;; Vectors are collections of values which are indexed by their position
;; in the vector (starting from 0) called **index**. Insertion at the end of the vector
;; is `near O(1)` as well as retrieval of an element by it's index.
;; The literals is expressed with a sequence of values surrounded by square
;; brackets or you can use the `vector` function to construct one.
;; You can append an element at the end of the vector with `conj` and use `get`
;; to retrieve an element in a specific index. Function such as `first`, `next`
;; `rest`, `last` and `count` will work just as fine with Vectors.

[1 2 3 4 5]
;;=> [1 2 3 4 5]

[1 "hi" :test 4/5 \c]
;;=> [1 "hi" :test 4/5 \c]

(vector 1 2 3 4 5)
;;=> [1 2 3 4 5]

(conj [1 2 3 4 5] 6)
;;=> [1 2 3 4 5 6]

(count [1 2])
;;=> 2

(first [:a :b :c])
;;=> :a

(get [:a :b :c] 1)
;;=> :b

([:a :b :c] 1)
;;=> :b

(get [:a :b :c] 10)
;;=> nil

(get [:a :b :c] 10 :z)
;;=> :z

;;
;; #### Maps
;;
;; Maps are associative data structures (often called dictionaries)
;; which maps keys to their corresponding value.
;; Maps have a literals form which it can be expressed by any number
;; of key/value pairs surrounded by curly brackets, or by using
;; `hash-map` or `array-map` functions. Hash-maps provides a `near O(1)`
;; insertion time and `near O(1)` seek time.
;; You can use `assoc` to "add or overwrite" an new pair, `dissoc` to
;; "remove" a key and its value, and use `get` to retrieve the value
;; of a given key.

{"jane" "jane@acme.com"
 "fred" "fred@acme.com"
 "rob"  "rob@acme.com"}
;;=> {"jane" "jane@acme.com", "fred" "fred@acme.com", "rob" "rob@acme.com"}

{:a 1, :b 2, :c 3}
;;=> {:a 1, :b 2, :c 3}

(hash-map :a 1, :b 2, :c 3)
;;=> {:c 3, :b 2, :a 1}

(array-map :a 1, :b 2, :c 3)
;;=> {:a 1, :b 2, :c 3}

(assoc {:a 1, :b 2, :c 3} :d 4)
;;=> {:a 1, :b 2, :c 3, :d 4}

(assoc {:a 1, :b 2, :c 3} :b 10)
;;=> {:a 1, :b 10, :c 3}

(dissoc {:a 1, :b 2, :c 3} :b)
;;=> {:a 1, :c 3}

(count {:a 1, :b 2, :c 3})
;;=> 3

(get {:a 1, :b 2, :c 3} :a)
;;=> 1

(get {:a 1, :b 2, :c 3} :a :not-found)
;;=> 1

(get {:a 1, :b 2, :c 3} :ZULU :not-found)
;;=> :not-found

(:a {:a 1, :b 2, :c 3})
;;=> 1

({:a 1, :b 2, :c 3} :a)
;;=> 1

;;
;; #### Sets
;;
;; Sets are a type of collection which doesn't allow for duplicate values.
;; While lists and vector can have duplicate elements, set eliminates
;; all duplicates.
;; Clojure has a literal form for sets which is expressed by a
;; sequence of values surrounded by `#{  }`. Otherwhise you
;; construct a set using the `set` function.
;; With `conj` you can "add" a new element to an existing set,
;; and `disj` to "remove" an element from the set.
;; With `clojure.set/union`, `clojure.set/difference` and
;; `clojure.set/intersection` you have typical sets operations.
;; `count` returns the number of elements in the set in `O(1)`
;; time.

#{1 2 4}
;;=> #{1 4 2}

#{ 1 1 3 5}
;;=> IllegalArgumentException Duplicate key: 1

#{:a 4 5 :d "hello"}
;;=> #{"hello" 4 5 :d :a}

(type #{:a :z})
;;=> clojure.lang.PersistentHashSet

(set [:a :b :c])
;;=> #{:c :b :a}

(conj #{:a :c} :b)
;;=> #{:c :b :a}

(conj #{:a :c} :c)
;;=> #{:c :a}

(disj #{:a :b :c} :b)
;;=> #{:c :a}

(clojure.set/union #{:a} #{:a :b} #{:c :a})
;;=> #{:c :b :a}

(clojure.set/difference #{:a :b} #{:c :a})
;;=> #{:b}

(clojure.set/intersection #{:a :b} #{:c :a})
;;=> #{:a}


;;
;; ### The sequence abstraction
;;
;; One of the most powerful abstraction of Clojure's data structures
;; is the `sequence` (`clojure.lang.ISeq`) which all data structure
;; implements. This interface resembles to a Java iterator,
;; and it implements methods like `first()`, `rest()`, `more()` and
;; `cons()`. The power of this abstraction is that is general enough
;; to be used in all data structures (lists, vectors, maps, sets and
;; even strings can all produce sequences) and you have loads of
;; functions which manipulates them. Functions such as `first`,
;; `rest`, `next` and `last` and many others such as `reverse`,
;; `shuffle`, `drop`, `take`, `partition`, `filter` etc
;; are all built on top of the sequence abstraction.
;; So if you create your own data-structure and you implement
;; the four methods of the `clojure.lang.ISeq` interface
;; you can benefit from all these function without having
;; to re-implement them for your specific data-structure.
;;
;; You can create a sequence explicitly with the `seq` function
;; but there are loads of function which already return a
;; sequence. The sequence of a list is the list itself,
;; other data-structures will produce one.
;; Maps will produce a sequence of map entry, where each
;; entry can be represented like a vector of two values
;; the key and it's value.
;;

(seq '(1 2 3 4))
;;=> (1 2 3 4)

(seq [1 2 3 4])
;;=> (1 2 3 4)

(seq #{1 2 3 4})
;;=> (1 4 3 2)

(seq {:a 1, :b 2, :c 3})
;;=> ([:a 1] [:b 2] [:c 3])

;;
;; There is no need to call `seq` explicitly in most of the cases
;; all function which takes a sequence can work with all data structures
;; directly.
;;

(first [1 2 3 4])
;;=> 1

(take 3 [:a :b :c :d])
;;=> (:a :b :c)

(shuffle [1 2 3 4])
;;=> [1 3 2 4]

(shuffle #{1 2 3 4})
;;=> [2 4 1 3]

(reverse [1 2 3 4])
;;=> (4 3 2 1)

(last (reverse {:a 1 :b 2 :c 3}))
;;=> [:a 1]


;;
;; #### Lazy Sequences
;;
;; Some of the sequences produced by the core library are lazy
;; which means that the entire collection won't be create,
;; but at first only the iterator like structure is created
;; while the consumer is fetching the `next()` item it will
;; be computed. This is a very important element of the language
;; which allows to express easily infinite sequences without running
;; out of memory. For example the function `range` returns a lazy sequence
;; of natural numbers between two given numbers. But when it is
;; called without arguments it returns a lazy sequence of all natural numbers.
;; Yet it doesn't run out of memory. What it really produce it is just a
;; iterator that computes the next number when the `next()` is called.
;;

(range 5 10)
;;=> (5 6 7 8 9)

(range)
;;=> (0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59 60 61 62 63 64 65 66 67 68 69 70 71 72 73 74 75 76 77 78 79 80 81 82 83 84 85 86 87 88 89 90 91 92 93 94 95 96 97 98 99 ...)

(take 10 (range))
;;=> (0 1 2 3 4 5 6 7 8 9)

;;
;; ### Regular expression patterns
;;
;; Clojure supports also regular expression patterns as literals
;; which directly map to the `java.util.Pattern` and offers a number
;; of function to match, find and extract patterns.
;;



;;
;; ### Symbols
;;



;;
;;
;;
