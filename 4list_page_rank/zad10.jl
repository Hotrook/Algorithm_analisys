include("pagerank.jl")
include("visualize.jl")

r = 32
alpha = 0.01
n = 4
input = [
0.0 0.3 0.1 0.6;
0.1 0.1 0.7 0.1;
0.1 0.7 0.1 0.1;
0.9 0.1 0.0 0.0;
]

pr_matrix = create_pagerank_matrix(input, alpha)
values, stationary_dist = pagerank(pr_matrix, r, alpha)

visualize(values)

println("a) Rozkład stacjonarny: ")
print("\t")
println([stationary_dist[1, i] for i = 1:n])


values, stationary_dist = pagerank(pr_matrix, 128, alpha)
println("b) Prawdopodobieństow znalezienia się w stanie 3 po 32 krokach,
    zacyznając w stanie 0:")
print("\t")
println(stationary_dist[1, 4])

println("c) Prawdopodobieństow znalezienia się w stanie 3 po 128 krokach,
    zacyznając w dowolnym stanie:")
print("\t")
println(stationary_dist[1, 4])


eps = [0.1, 0.01, 0.001]
min_t = [0, 0, 0]

for i = 1:100
    _, sd = pagerank(pr_matrix, i, alpha)
    for k = 1:3
        temp, _ = findmax([abs(sd[1, j] - stationary_dist[1, j]) for j = 1:n])
        if temp < eps[ k ] &&  min_t[ k ] == 0
            min_t[ k ] = i
        end
    end
end

println("d) Minimalne t dla epsilonów")
for i = 1:3
    println("\t$(eps[i]): $(min_t[i])")
end
