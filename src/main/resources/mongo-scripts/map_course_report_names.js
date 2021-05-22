/*
the map function iterates through each document in the student collection and emits an object per key
which are collected in memory in an array allocated per key
*/
function map() {
  emit(this._id, {name: this.name.first+' '+this.name.last, numbercourses: 0});
}