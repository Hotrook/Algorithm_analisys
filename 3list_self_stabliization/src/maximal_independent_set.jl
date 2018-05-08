using Graphs

function legal_state( g::GenericGraph, v::Int, in_set::Vector{Int} )
    neighbours_in_set = 0

    for n in out_neighbors(v, g)
        neighbours_in_set += in_set[ n ]
    end

    if neighbours_in_set > 0 && in_set[ v ] == 1
        return false
    elseif neighbours_in_set == 0 && in_set[ v ] == 0
        return false
    end
    return true
end

function legal( g::GenericGraph, in_set::Vector{Int} )
    illegal_counter = 0

    for v in vertices(g)
        if !legal_state(g, v, in_set)
            return false
        end
    end
    return true
end

function flip(v::Int, in_set::Vector{Int})
    in_set[ v ] = 1 - in_set[ v ]
end

function simulate( g::GenericGraph, in_set::Vector{Int} )
    if legal(g, in_set)
        return
    end

    for v in vertices(g)
        if !legal_state(g, v, in_set)
            flip(v, in_set)
            simulate(g, in_set)
            flip(v, in_set)
        end
    end
end

function increase(in_set::Vector{Int}, pos::Int)
    if pos == 0
        return
    end

    if in_set[ pos ] == 0
        in_set[ pos ] = 1
    else
        in_set[ pos ] = 0
        increase(in_set, pos-1)
    end
end

function next_configuration(in_set::Vector{Int}, n::Int)
    increase(in_set, n)
end

function check_all_configurations(g::GenericGraph)
    n = num_vertices(g)
    in_set = zeros(Int, n )
    bound = 2 ^ n
    it = 0

    while it < bound
        print(in_set)
        simulate(g, in_set)
        println(" OK")
        next_configuration(in_set, n)
        it += 1
    end
end

function add_edges(g::GenericGraph, z::Matrix{Int})
    (n,m) = size(z)

    for i = 1:n, j = 1:n
        if z[i, j] == 1
            add_edge!(g, i, j)
        end
    end
end

g = simple_graph(7, is_directed=false)

check_all_configurations(g)

z = [
0 1 0 0 1 0 0;
0 0 1 0 0 1 0;
0 0 0 1 0 0 1;
1 0 0 0 1 0 0;
0 1 0 0 0 1 0;
0 0 1 0 0 0 1;
1 0 0 1 0 0 0;
]

add_edges(g, z)


check_all_configurations(g)
