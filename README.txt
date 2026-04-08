Sahon Shaha - 40339419

A2 is still compatible with A3 because the loading and saving format remains unchanged.
The only addition to A3 is that the parsing to CSV row is done within the object class itself rather than a general class.
To use the A2 version of the file loading and saving, the loadAll and saveAll methods pass a boolean set to true.

LinkedList Justification for recentList:
For recentList, it is better to use linked list because it is easier to retrieve the last element of it rather than using the ArrayList.
In the ArrayList, to store up to 10 elements, we would have to shift all the elements everytime.
This would make it slower than using a LinkedList where there is no shifting involved and we just need to disconnect nodes.