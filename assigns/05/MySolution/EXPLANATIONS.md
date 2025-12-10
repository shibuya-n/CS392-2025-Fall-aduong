ASSIGN05_02
The main issue was in the insertIntoSorted method. The original implementation had this problem:

Built prefix in reverse - collected elements smaller than elem into a prefix list
Then reversed it again - when building the final result, it reversed the prefix back

This double-reversal approach works correctly but creates unnecessary overhead. However, the real problem is more subtle - the way the lists were being constructed led to inefficient memory access patterns.
The fixed version:

Still builds elements in reverse (which is necessary for singly-linked lists)
But does a cleaner single reversal pass
Maintains the same algorithmic complexity but with better constant factors
