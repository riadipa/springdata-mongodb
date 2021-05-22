/*
the map function iterates through the students object of each document in the course collection
and emits an object per key which are collected in memory in an array allocated per key
*/
function() {
  this.students.forEach(function(value) {
    emit(value, {name: '', numbercourses: 1});
  });
}