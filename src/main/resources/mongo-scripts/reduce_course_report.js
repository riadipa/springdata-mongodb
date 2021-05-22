/*
the reduce function works on all the array items in memory associated with every key emitted in the map phase
and process the data to form a new collection of object per key
*/
function reduce(key, values) {
    var result = {name: '', numbercourses: 0};
 
    values.forEach(function(value) {
      result.numbercourses += value.numbercourses;
      if (result.name === '') {
        result.name = value.name;
      }
    });
 
    return result;
}