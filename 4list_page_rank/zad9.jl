include("pagerank.jl")
include("visualize.jl")

r = 32
alpha = 0.0
input = [
1 0 0 0 0 0;
0 0 0 0 1 0;
1 0 0 0 0 0;
0 1 0 0 1 0;
0 0 0 1 0 0;
0 0 1 0 0 0;
]

pr_matrix = create_pagerank_matrix(input, alpha)
values, stationary_dist = pagerank(pr_matrix, r, alpha)

println(values[6, 32])
println(values[5, 32])
println(values[4, 32])
println(values[3, 32])
println(values[2, 32])
println(values[1, 32])
visualize(values)

plot(fill(1.0,20))
