SELECT DISTINCT author
FROM (
	SELECT movie.mid AS mid, person.name AS author
	FROM movie
		JOIN writes ON writes.mid = movie.mid
		JOIN person ON person.pid = writes.pid
) AS table1 
	JOIN acts ON table1.mid = acts.mid
	JOIN person ON acts.pid = person.pid
WHERE person.name = 'Harrison Ford'
