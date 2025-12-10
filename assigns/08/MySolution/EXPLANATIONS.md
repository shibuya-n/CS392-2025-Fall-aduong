ASSIGN08_01

- search$raw
  -- if the key wasn't in the table, findEntry returned null, so entry.sub1 would throw a NullPointerExeception. Now, search$raw safely returns nulll when the key is absent.

- wrong LnList methods in remove$raw
  -- now uses the ocrrect unlin1/link1 methods

ASSIGN08_02

- added occupied array
  -- with only deleted[], you can't distinguish between a slot that was never used and a slot that was used but then deleted, causing issues in findIndex() when checking if table[idx] is null and not deleted

- fixed findIndex() logic
  -- now checks if slot was never used

- fixed insert$raw()
  -- set occupied[]
