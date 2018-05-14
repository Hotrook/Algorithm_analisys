function create_pagerank_matrix(M::Matrix{Float64}, alpha::Float64)
    (n, m) = size(M)
    M = (1 - alpha) * M + alpha * fill(1.0/n, n, n)
    return M
end

function create_pagerank_matrix(M::Matrix{Int}, alpha::Float64)
    (n, m) = size(M)
    pr_matrix = fill(0.0, n, n)

    for i = 1:n
        nonzeros::Float64 = sum([M[i, j] for j = 1:n])
        for j = 1:n
            if nonzeros != 0
                pr_matrix[i, j] = M[i, j] / nonzeros
            else
                pr_matrix[i, j] = convert(Float64, M[i, j])
            end
        end
    end

    return create_pagerank_matrix(pr_matrix, alpha)
end

function find_stationary_distribution(M::Matrix, r::Int)
    (n, m) = size(M)
    values = fill(0.0, n, r)
    mult_matrix = 1

    for i = 1:r
        mult_matrix *= M
        for j = 1:n
            values[ j, i ] = mean([mult_matrix[k, j] for k = 1:n])
        end
    end

    return values, mult_matrix
end

function pagerank(pr_matrix::Matrix, k::Int, alpha::Float64)
    # pr_matrix = create_pagerank_matrix(M, alpha)
    values, stationary_dist = find_stationary_distribution(pr_matrix, k)
    return values, stationary_dist
end

function find_stationary_distribution(M::Matrix, r::Int, init::Vector)
    (n, m) = size(M)
    values = fill(0.0, n, r)
    vec = transpose(init)

    for i = 1:r
        vec *= M
        for j in 1:n
            values[j, i] = vec[ j ]
        end
    end

    return values, vec
end

function pagerank(pr_matrix::Matrix, k::Int, alpha::Float64, init::Vector)
    # pr_matrix = create_pagerank_matrix(M, alpha)
    values, stationary_dist = find_stationary_distribution(pr_matrix, k, init)
    return values, stationary_dist
end
