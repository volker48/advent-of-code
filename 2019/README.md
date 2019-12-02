# Lessons learned

1. Read the problem closely. I initially forgot to do the subtraction step.
2. 
    1. Pattern matching Arrays can be annoying. I attempted to match `Array(1, x, y, index, tail @ _*)`, but this causes
tail to become a `Seq` and I could no longer recursively call my process function. As a work around I just converted the
instructions to a `List`, but there is probably a better way.
    2. I hadn't used for comprehensions before and used it to generate all the noun, verb combinations.
    3. It isn't as easy to "break" in Scala. I wanted to break out of the for comprehension when the correct result is 
    found, but I would need to restructure the code so I gave up on it.
