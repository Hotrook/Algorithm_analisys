include("pagerank.jl")
include("visualize.jl")

n = 5
r = 25
alphas = [0.0, 0.25, 0.5, 0.75, 0.85, 1.0]
init = fill(0.2, n)
input = [
0 1 1 0 0;
0 0 0 1 0;
0 1 0 1 1;
1 0 0 0 0;
0 0 0 0 0;
]

for alpha in alphas
    pr_matrix = create_pagerank_matrix(input, alpha)
    values, stationary_dist = pagerank(pr_matrix, r, alpha, init)

    filename = "chart$alpha.jl"
    visualize(values, filename)
end
