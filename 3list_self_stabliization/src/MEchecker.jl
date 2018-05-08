
function legal_move(state::Vector{Int}, i::Int, n::Int)
    if i == 1
        if state[ 1 ] == state[ n ]
            return true
        else
            return false
        end
    else
        if state[ i ] != state[ i-1 ]
            return true
        else
            return false
        end
    end
end

function legal(state::Vector{Int}, n::Int)
    legal_points = 0

    for i = 1:n
        if legal_move(state, i, n)
            legal_points += 1
        end
    end

    if legal_points == 1
        return true
    end

    return false
end

function do_move(state::Vector{Int}, i::Int, n::Int)
    original = state[ i ]
    if i == 1
        state[ 1 ] = (state[ n ] + 1) % (n+1)
    else
        state[ i ] = state[ i - 1 ]
    end
    return original
end

function find_max(state::Vector{Int}, dict::Dict)
    n = size(state)[1]

    steps = get(dict, string(state), -1 )
    if steps >= 0
        return steps
    end

    if legal(state, n)
        return 0
    end

    max_steps = 0

    for i = 1:n
        if legal_move(state, i, n)
            original = do_move(state, i, n)
            steps = find_max(state, dict)
            max_steps = max(max_steps, steps)
            state[ i ] = original
        end
    end

    dict[ string(state) ] = max_steps+1
    return max_steps+1
end

function decrease(state::Vector{Int}, pos::Int, n::Int)
    if pos == 0
        return
    elseif state[pos] > 0
        state[ pos ] -= 1
    else
        state[ pos ] = n
        decrease(state, pos-1, n)
    end
end

function next_configuration(state::Vector{Int}, n::Int)
    decrease(state, n, n)
end

function check_all_configurations(n::Int)
    conf = fill(n, n)
    bound = (n+1) ^ n
    it = 0
    max_steps = 0


    while it < bound
        if it % 1000 == 0
            x = it / bound
            @printf("\r%3.5f", x )
        end
        steps = find_max(conf, dict)
        max_steps = max(max_steps, steps)
        next_configuration(conf, n)
        it += 1
    end

    println()
    println("Max found steps: $max_steps")
end


dict = Dict()
@time check_all_configurations(7)
