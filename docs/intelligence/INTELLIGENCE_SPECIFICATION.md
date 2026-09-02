# VESTIGE — Intelligence & Sustainability Methodology (Phase 15)

## 1. Device Evaluation & Recommendation Algorithm
The `DecisionEngineService` scores device restorability from **5 to 95** using a multi-factor weighted heuristic:

1. **Economic Cost-to-Value Ratio (Weight ~35%)**:
   - Compares estimated repair costs against original/benchmark valuation.
   - Low ratio (<20%) yields maximum score bonus; ratios >60% diminish restoration feasibility.
2. **Component Age & Technology Obsolescence (Weight ~25%)**:
   - Evaluates category-specific lifespan curves (Smartphones: >3/5 yrs; Laptops: >5/7 yrs; Desktops: >5/8 yrs).
3. **Physical & Structural Condition (Weight ~20%)**:
   - Evaluates chassis and enclosure integrity (`GOOD`, `FAIR`, `POOR`).
4. **Spare Parts Availability (Weight ~20%)**:
   - Factors in component availability (`AVAILABLE`, `UNKNOWN`, `UNAVAILABLE`).
5. **Critical Fault Diagnostic Penalty**:
   - Severe motherboard/liquid short circuit faults incur targeted deductions.

## 2. Environmental Life-Cycle Assessment (LCA) Methodology
All environmental benefit estimates follow standard circular economy LCA empirical benchmarks:
- **Smartphones**: ~0.18 kg e-waste diverted; ~45 kg CO₂e saved per extended lifecycle.
- **Tablets**: ~0.45 kg e-waste diverted; ~60 kg CO₂e saved.
- **Laptops**: ~2.10 kg e-waste diverted; ~180 kg CO₂e saved.
- **Desktops**: ~8.50 kg e-waste diverted; ~240 kg CO₂e saved.
- **Other Electronics**: ~0.80 kg e-waste diverted; ~30 kg CO₂e saved.

*Note: All values in rationales are explicitly presented as estimates ("Est. ~X kg") to maintain complete scientific integrity and transparency.*

## 3. Failure Fallbacks & Anomaly Detection
- If optional evaluation parameters are absent, default baselines are applied without throwing runtime exceptions.
- Anomaly alerts flag unusual operational deviations (e.g. abrupt registration surges, payment failures) for human administrator review without taking automated destructive actions.
