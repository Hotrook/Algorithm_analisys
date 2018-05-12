include("pagerank.jl")
include("visualize.jl")

r = 32
alpha = 0.5
input = [
1 0 0 0 0 0;
0 0 1 0 1 0;
1 0 0 0 0 0;
0 1 0 0 1 0;
0 0 0 1 0 0;
0 0 1 0 0 0;
]

pr_matrix = create_pagerank_matrix(input, alpha)
values, stationary_dist = pagerank(pr_matrix, r, alpha)

visualize(values)
