# Assignment 5

It is due Tuesday, the 14th of October, 2025.

## Assignment 5-1 (30 points)

Please REWRITE the mergesort given in the following file:
Library/FnList/FnListSUTIL.java
to make it suitable for sorting a long list (consisting of 1M
numbers).  You may want to study the quicksort implementation also
given in the same Java source file, which is capable of sorrting very
long lists.

Your implementation of mergesort should be STABLE.

Please see Code/Assign05_01.java for some given code.

## Assignment 5-2 (30 points)

An implementation of insertion sort is given in the following file:
Library/FnList/FnListSUTIL.java
which is NOT loop-based. As such, if it is applied to a very long list,
a stack overflow exception is thrown. Please REWRITE the implementation to
make it loop-based.

Your implementation of insertion sort should be STABLE.

Please see Code/Assign05_02.java for some given code.

If you have done your implementation correctly, then this implementation
should be able to sort the following list of 1M numbers efficiently (that is,
in linear time).

1, 0, 3, 2, 5, 4, 7, 6, 9, 8, 11, 10, ..., 999999, 999998
