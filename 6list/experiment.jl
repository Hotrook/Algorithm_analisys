
function experiment(n::Int)
    count = 0

    if n >= 2
        for k = 1:n
            if rand() < 0.5
                count += experiment(k)
            end
        end
    end

    return count + 1
end

result = 0

for i = 1:1000
    result += experiment(8)
end

println(result/1000)
# 3 * 2^n - 2
