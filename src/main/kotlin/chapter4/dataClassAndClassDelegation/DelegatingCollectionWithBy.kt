package chapter4.dataClassAndClassDelegation

class DelegatingCollectionWithBy<T>(innerList:Collection<T> = ArrayList<T>()): Collection<T> by innerList {
}