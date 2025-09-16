# Assignment 3

It is due Tuesday, the 23rd of September, 2025

## Assignment 3-1 (10 points)

The MacCarthy's 91-function (in Python's syntax)
is defined below:

def f91(n):
  return (n-10) if n > 100 else f91(f91(n+11))

There are two calls to f91; the outer one is a
tail-call but the inner one is not.

Please translate the above definition into an
implementation of f91 in Java that is tail-recursive,
that is, each recursive call made in this implementation
must be a tail-call.

## Assignment 3-2 (10 points)

Please solve the parenthesis/bracket/brace balance
problem. For instance, "({()[({})]})" is balanced, but
"({()[({})])}" is not. Your solution must make proper use
of the MyStack data structure.

## Assignment 3-3 (30 points)

Please give a stack implementation based on two functional lists.
If this is not clear to you, please search "two list based queue
implementation".
