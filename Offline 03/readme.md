## Offline 03

### Exam Scheduling with Local Search

***
### Implementation
1. Courses are nodes.
2. Constraints are edges.
3. Implemented the Hard Constraint hardcoded but checked for it in Perturbative Heuristics.

***
### To Do
- [x] Same pair of nodes don't create another edge.
- [x] Average penalty is still faulty. It's just adding one edge once but one edge may represent multiple students.
- [x] Also same edge is being calculated in the penalty twice.
- [x] Kempe Chain written. Don't know if it works for sure.
- [x] Didn't check hard constraint after Kempe Chain Interchange. Need to to do it.
- [x] 1000 iteration only for upgrade not all.
- [x] Pair Swap is taking two non adjacent nodes (randomly). Interchange their color and check for hard constraint. To do thise can take their individual Kempe chains and to check if they have only one node in them.
- [ ] Does my pair swap work for sure?