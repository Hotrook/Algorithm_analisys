
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

    steps::UInt8 = get(dict, string(state), 127 )
    if steps != 127
        return steps
    end

    if legal(state, n)
        return 0
    end

    max_steps::UInt8 = 0
    original = 0

    for i = 1:n
        if legal_move(state, i, n)
            original = do_move(state, i, n)
            steps = find_max(state, dict)
            max_steps = max(max_steps, steps)
            state[ i ] = original
        end
    end

    dict[ string(state) ] = max_steps+convert(UInt8,1)
    return max_steps+convert(UInt8,1)
end

function transform(state::Vector{Int}, n::Int)
    n1 = n+1
    x = 1
    sum = 1

    for i = n:-1:1
        sum += x*state[i]
        x *= n1
    end

    return sum
end

function find_max(state::Vector{Int}, tab::Vector{UInt8})
    n = size(state)[1]

    steps::UInt8 = tab[transform(state, n)]
    if steps != 127
        return steps
    end

    if legal(state, n)
        return 0
    end

    max_steps::UInt8 = 0
    original = 0

    for i = 1:n
        if legal_move(state, i, n)
            original = do_move(state, i, n)
            steps = find_max(state, tab)
            max_steps = max(max_steps, steps)
            state[ i ] = original
        end
    end

    tab[ transform(state, n) ] = max_steps+convert(UInt8,1)
    return max_steps+convert(UInt8,1)
end

function decrease(state::Vector{Int}, pos::Int, n::Int)
    if pos == 0
        return
    elseif state[pos] > 0
        state[ pos ] -= 1
    else
        state[ pos ] = n-1
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
    max_steps::UInt8 = 0
    steps::UInt8 = 0


    println(bound)
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


function check_all_configurations(n::Int, tab::Vector{UInt8})
    conf = fill(n-1, n)
    bound = (n+1) ^ n
    it = 0
    max_steps::UInt8 = 0
    steps::UInt8 = 0


    println(bound)
    while it < bound
        if it % 1000 == 0
            x = it / bound
            @printf("\r%3.5f", x )
        end
        steps = find_max(conf, tab)
        max_steps = max(max_steps, steps)
        next_configuration(conf, n)
        it += 1
    end

    println()
    println("Max found steps: $max_steps")
end

n = 3

# dict = Dict{String, UInt8}()
# sizehint!(dict, (n+1) ^ n)
# @time check_all_configurations(n)
x = convert(UInt8, 127)
@time tab = fill( x, (n+1)^n )
@time check_all_configurations(n, tab)
